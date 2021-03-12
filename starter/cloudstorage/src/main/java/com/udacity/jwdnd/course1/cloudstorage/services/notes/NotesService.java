package com.udacity.jwdnd.course1.cloudstorage.services.notes;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotesService {
    private StatusMessageService statusMessageService;
    private NotesMapper notesMapper;

    public NotesService(StatusMessageService statusMessageService, NotesMapper notesMapper) {
        this.statusMessageService = statusMessageService;
        this.notesMapper = notesMapper;
    }

    public List<NoteForm> getNotes(Integer userId) {
        return notesMapper.getAllNotes(userId);
    }

    public NoteForm getNote(Integer noteId) {
        return notesMapper.getNote(noteId);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }

    public void updateNote(NoteForm note){
        notesMapper.updateNote(note);
    }

    public void addNote(Integer noteId, Integer userId, String noteTitle, String noteDescription) throws IOException {
        NoteForm noteFormPojo = getNoteObject(noteId, userId, noteTitle, noteDescription);
        notesMapper.insertNote(noteFormPojo);
    }

    private NoteForm getNoteObject(Integer noteId, Integer userId, String noteTitle, String noteDescription) throws IOException {
        NoteForm noteForm = new NoteForm();
        noteForm.setNoteid(noteId);
        noteForm.setUserid(userId);
        noteForm.setNotetitle(noteTitle);
        noteForm.setNotedescription(noteDescription);
        return noteForm;
    }
}
