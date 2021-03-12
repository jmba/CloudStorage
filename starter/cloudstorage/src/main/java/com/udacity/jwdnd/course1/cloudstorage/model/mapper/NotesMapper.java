package com.udacity.jwdnd.course1.cloudstorage.model.mapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    List<NoteForm> getAllNotes(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteid}")
    NoteForm getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid)" +
            "values (#{noteid}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(NoteForm noteForm);

    @Update("UPDATE notes set notetitle=#{notetitle}, notedescription=#{notedescription} " +
            "where noteid=#{noteid}")
    int updateNote(NoteForm noteForm);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteid}")
    void deleteNote(Integer noteid);
}