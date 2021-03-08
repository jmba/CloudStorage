package com.udacity.jwdnd.course1.cloudstorage.model.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * from files where userid=#{userid}")
    List<File> getAllFiles(Integer userid);

    @Select("SELECT * from files where filename=#{filename} and userId=#{userid}")
    File getFileByName(String filename, Integer userid);

    @Insert("INSERT into files (fileid, filename, contenttype, filesize, userid, filedata)" +
            "values (#{fileid}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(File file);

    @Delete("DELETE from files where filename=#{filename} and userId=#{userid}")
    void deleteFile(String filename, Integer userid);
}
