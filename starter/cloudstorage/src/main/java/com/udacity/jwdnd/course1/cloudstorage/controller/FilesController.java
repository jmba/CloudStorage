package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.FileForm;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import static com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService.MessageType.FILES;

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
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file,
                             Model model,
                             Authentication authentication) {

        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            fileService.addFile(file, userId);
            messageService.addMessage(FILES, "FileForm: " + file.getOriginalFilename() + " successfully uploaded.");
        } catch (IOException e) {
            messageService.addMessage(FILES, "Error while uploading FileForm: " + file.getOriginalFilename());
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(Model model,
                             @RequestParam(name = "fileName", required = false) String fileName,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            fileService.deleteFile(fileName, userId);
            messageService.addMessage(FILES, "FileForm: " + fileName + " successfully deleted.");
        } catch (Exception e) {
            messageService.addMessage(FILES, "Error while deleting FileForm: " + fileName);
            e.printStackTrace();
        }

        model.addAttribute("statusMessages", messageService.getStatusMessages(FILES));
        model.addAttribute("files", fileService.getFiles(userId));
        redirectAttributes.addFlashAttribute("setTab", "FileTab");
        return "redirect:/home";
    }

    @GetMapping("/viewFile/{fileName}")
    public ResponseEntity<ByteArrayResource> viewFile(Model model,
                                                      @RequestParam String fileName,
                                                      Authentication authentication,
                                                      RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        ResponseEntity<ByteArrayResource> response = null;

        try {
            response = getResponse(fileName, userId);
            messageService.addMessage(FILES, "FileForm: " + fileName + " successfully downloaded.");
        } catch (Exception e) {
            messageService.addMessage(FILES, "Error while downloading FileForm: " + fileName);
            e.printStackTrace();
        }

        model.addAttribute("statusMessages", messageService.getStatusMessages(FILES));
        redirectAttributes.addFlashAttribute("setTab", "FileTab");
        return response;
    }

    private ResponseEntity<ByteArrayResource> getResponse(String fileName, Integer userId) {
        FileForm fileForm = fileService.getFile(fileName, userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileForm.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForm.getFilename() + "\"")
                .body(new ByteArrayResource(fileForm.getFiledata()));
    }
}