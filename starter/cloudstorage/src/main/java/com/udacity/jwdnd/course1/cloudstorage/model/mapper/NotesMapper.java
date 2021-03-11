package com.udacity.jwdnd.course1.cloudstorage.model.mapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    List<NoteForm> getAllNotes(Integer userid);

//    @Select("SELECT * FROM NOTES WHERE filename=#{filename} AND userId=#{userid}")
//    FileForm getFileByName(String filename, Integer userid);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid)" +
            "values (#{noteid}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(NoteForm noteForm);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteid} AND userid=#{userid}")
    void deleteNote(Integer noteid, Integer userid);
}