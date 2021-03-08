package com.udacity.jwdnd.course1.cloudstorage.services.notes;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.Note;
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

    public List<Note> getNotes(Integer userId) {
        return notesMapper.getAllNotes(userId);
    }

    public void deleteNote(Integer noteId, Integer userId) {
        notesMapper.deleteNote(noteId, userId);
    }

    public void addNote(Integer noteId, Integer userId, String noteTitle, String noteDescription) throws IOException {
        Note notePojo = getNoteObject(noteId, userId, noteTitle, noteDescription);
        notesMapper.insertNote(notePojo);
    }

    private Note getNoteObject(Integer noteId, Integer userId, String noteTitle, String noteDescription) throws IOException {
        Note note = new Note();
        note.setNoteid(noteId);
        note.setUserid(userId);
        note.setNotetitle(noteTitle);
        note.setNotedescription(noteDescription);
        return note;
    }
}
