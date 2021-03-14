package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.CredentialForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<CredentialForm> getAllCredentials(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    CredentialForm getCredentialsById(Integer credentialId);

    @Update("UPDATE CREDENTIALS set credentialid=#{credentialid}, url=#{url} , username=#{username} , " +
            "key=#{key} , password=#{password} , userid=#{userid}" + "where credentialid=#{credentialid}")
    int updateCredentials(CredentialForm credentialForm);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    void deleteCredentials(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid)" +
            "values (#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentials(CredentialForm credentials);
}