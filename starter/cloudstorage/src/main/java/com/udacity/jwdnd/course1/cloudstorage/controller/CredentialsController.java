package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.credentials.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.credentials.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService.MessageType.CREDENTIALS;

@Controller
public class CredentialsController {
    private StatusMessageService messageService;
    private CredentialsService credentialsService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialsController(StatusMessageService messageService, CredentialsService credentialsService, UserService userService, EncryptionService encryptionService) {
        this.messageService = messageService;
        this.credentialsService = credentialsService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/deleteCredentials/{credentialid}")
    public String deleteCredentials(@PathVariable("credentialid") Integer credentialid, RedirectAttributes redirectAttributes) {
        try {
            credentialsService.deleteCredentials(credentialid);
            messageService.addMessage(CREDENTIALS, "Credentials deleted.");
        } catch (Exception e) {
            messageService.addMessage(CREDENTIALS, "Error while deleting credentials.");
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("setTab", "CredentialTab");
        return "redirect:/home";
    }

    @PostMapping("/addCredentials")
    public String addCredentials(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        CredentialForm existingCredentials = credentialsService.getCredentials(credentialForm.getCredentialid());

        if (existingCredentials != null) {
            updateCredentials(existingCredentials, credentialForm);
        } else {
            createCredentials(credentialForm, userId);
        }

        redirectAttributes.addFlashAttribute("setTab", "CredentialTab");
        return "redirect:/home";
    }

    private void createCredentials(CredentialForm credentialForm, Integer userId) {
        try {
            credentialsService.addCredentials(
                    null,
                    credentialForm.getUsername(),
                    credentialForm.getPassword(),
                    credentialForm.getUrl(),
                    userId);
            messageService.addMessage(CREDENTIALS, "Credentials created.");
        } catch (Exception e) {
            messageService.addMessage(CREDENTIALS, "Error while creating credentials.");
            e.printStackTrace();
        }
    }

    private void updateCredentials(CredentialForm existingCredentials, CredentialForm newCredentials) {
        try {
            credentialsService.updateCredentials(existingCredentials,
                    newCredentials.getUsername(),
                    newCredentials.getPassword(),
                    newCredentials.getUrl());
            messageService.addMessage(CREDENTIALS, "Credentials updated.");
        } catch (Exception e) {
            messageService.addMessage(CREDENTIALS, "Error while updating credentials.");
            e.printStackTrace();
        }
    }
}
