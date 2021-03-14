package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.FileForm;
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
import org.springframework.util.StringUtils;
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
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            if(StringUtils.isEmpty(file.getOriginalFilename())){
                messageService.addMessage(FILES, "Choose a file you want to upload.");
                return "redirect:/home";
            }

            if(fileService.getFile(file.getOriginalFilename(), userId) != null){
                messageService.addMessage(FILES, "File already exists.");
                return "redirect:/home";
            }

            fileService.addFile(file, userId);
            messageService.addMessage(FILES, "File successfully uploaded.");
        } catch (IOException e) {
            messageService.addMessage(FILES, "Error while uploading File: " + file.getOriginalFilename());
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(@RequestParam String fileName, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        try {
            fileService.deleteFile(fileName, userId);
            messageService.addMessage(FILES, "File deleted");
        } catch (Exception e) {
            messageService.addMessage(FILES, "Error while deleting File: " + fileName);
            e.printStackTrace();
        }

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
            messageService.addMessage(FILES, "File downloaded");
        } catch (Exception e) {
            messageService.addMessage(FILES, "Error while downloading FileForm: " + fileName);
            e.printStackTrace();
        }

        model.addAttribute("statusMessagesFiles", messageService.getStatusMessages(FILES));
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