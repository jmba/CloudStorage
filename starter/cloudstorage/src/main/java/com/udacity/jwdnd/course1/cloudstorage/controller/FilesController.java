package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.File;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.files.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FilesController {
    private StatusMessageService messageService;
    private FileService fileService;
    private UserService userService;

    public FilesController(StatusMessageService messageService, FileService uploadService, UserService userService) {
        this.messageService = messageService;
        this.fileService = uploadService;
        this.userService = userService;
    }

    @PostMapping("/fileUpload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            fileService.addFile(file, userId);
            messageService.addMessage("File: " + file.getOriginalFilename() + " successfully uploaded.");
        } catch (IOException e) {
            messageService.addMessage("Error while uploading File: " + file.getOriginalFilename());
            e.printStackTrace();
        }

        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/files")
    public String downloadFile(Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(Model model, @RequestParam(name = "fileName", required = false) String fileName, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            fileService.deleteFile(fileName, userId);
            messageService.addMessage("File: " + fileName + " successfully deleted.");
        } catch (Exception e) {
            messageService.addMessage("Error while deleting File: " + fileName);
            e.printStackTrace();
        }

        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/viewFile/{fileName}")
    public ResponseEntity<ByteArrayResource> viewFile(Model model, @RequestParam String fileName, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        ResponseEntity<ByteArrayResource> response = null;

        try {
            response = getResponse(fileName, userId);
            messageService.addMessage("File: " + fileName + " successfully downloaded.");
        } catch (Exception e) {
            messageService.addMessage("Error while downloading File: " + fileName);
            e.printStackTrace();
        }

        model.addAttribute("statusMessages", messageService.getStatusMessages());
        return response;
    }

    private ResponseEntity<ByteArrayResource> getResponse(String fileName, Integer userId) {
        File file = fileService.getFile(fileName, userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

}