package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<NoteForm> getAllNotes(Integer userid);

//    @Select("SELECT * FROM NOTES WHERE filename=#{filename} AND userId=#{userid}")
//    FileForm getFileByName(String filename, Integer userid);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid)" +
            "values (#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertFile(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid} AND userid=#{userid}")
    void deleteFile(String credentialid, Integer userid);
}