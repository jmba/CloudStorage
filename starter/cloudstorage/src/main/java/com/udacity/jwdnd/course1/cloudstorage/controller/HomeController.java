package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.authentication.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.credentials.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.files.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.notes.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NotesService notesService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NotesService notesService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.notesService = notesService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String initHome(Model model, StatusMessageService messageService, Authentication authentication){
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles(userId));
        model.addAttribute("notes", notesService.getNotes(userId));
        return "home";
    }
}