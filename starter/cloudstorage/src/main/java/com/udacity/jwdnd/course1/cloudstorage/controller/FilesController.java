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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        fileService.addFile(file, userId);
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/fileUpload")
    public String downloadFile(Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(Model model, @RequestParam(name = "fileName", required = false) String fileName, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        fileService.deleteFile(fileName, userId);
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        return "home";
    }

    @GetMapping("/viewFile/{fileName}")
    public ResponseEntity<ByteArrayResource>  viewFile(Model model,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       @RequestParam String fileName,
                                                       Authentication authentication) throws IOException {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        File file = fileService.getFile(fileName, userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

}