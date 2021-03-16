package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.credentials.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.credentials.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.files.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.notes.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService.MessageType.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;
    private StatusMessageService messageService;


    public HomeController(UserService userService,
                          FileService fileService,
                          NotesService notesService,
                          CredentialsService credentialsService,
                          EncryptionService encryptionService,
                          StatusMessageService messageService) {
        this.userService = userService;
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.messageService = messageService;
    }

    @GetMapping
    public String initHome(Model model,
                           Authentication authentication,
                           @ModelAttribute("noteForm") NoteForm noteForm,
                           @ModelAttribute("setTab") String setTab){
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getFiles(userId));
        model.addAttribute("notes", notesService.getNotes(userId));
        model.addAttribute("credentials", credentialsService.getAllCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("statusMessagesSuccess", messageService.getStatusMessages(SUCCESS));
        model.addAttribute("statusMessagesError", messageService.getStatusMessages(ERROR));
        model.addAttribute("setTab", setTab);
        return "home";
    }

}