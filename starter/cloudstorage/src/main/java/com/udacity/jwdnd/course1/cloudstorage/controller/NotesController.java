package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.notes.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService.MessageType.*;

@Controller
public class NotesController {
    private StatusMessageService messageService;
    private NotesService notesService;
    private UserService userService;

    public NotesController(StatusMessageService messageService, NotesService notesService, UserService userService) {
        this.messageService = messageService;
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping("/deleteNote/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteid, RedirectAttributes redirectAttributes){
        try {
            notesService.deleteNote(noteid);
            messageService.addMessage(SUCCESS, "Note deleted.");
        } catch (Exception e) {
            messageService.addMessage(ERROR, "Error while deleting Note.");
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("setTab", "NoteTab");
        return "redirect:/home";
    }

    @PostMapping("/addNote")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes){
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        NoteForm existingNote = notesService.getNote(noteForm.getNoteid());
        if(existingNote != null){
            updateNote(existingNote, noteForm);
        }else{
            createNote(noteForm, userId);
        }

        redirectAttributes.addFlashAttribute("setTab", "NoteTab");
        return "redirect:/home";
    }

    private void createNote(NoteForm noteForm, Integer userId){
        try {
            notesService.addNote(null, userId,  noteForm.getNotetitle(), noteForm.getNotedescription());
            messageService.addMessage(SUCCESS, "Note created.");
        } catch (Exception e) {
            messageService.addMessage(ERROR, "Error while creating note: " + noteForm.getNotetitle());
            e.printStackTrace();
        }
    }

    private void updateNote(NoteForm existingNote, NoteForm newData){
        try {
            existingNote.setNotetitle(newData.getNotetitle());
            existingNote.setNotedescription(newData.getNotedescription());
            notesService.updateNote(existingNote);
            messageService.addMessage(SUCCESS, "Note updated.");
        } catch (Exception e) {
            messageService.addMessage(ERROR, "Error while updating note: " + newData.getNotetitle());
            e.printStackTrace();
        }
    }
}
