package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotesController {
    @GetMapping("/notes")
    public String getNodes(Model model){

        return "home";
    }

    @PostMapping("/addNote")
    public String addNote(Model model){
        return "home";
    }
}
