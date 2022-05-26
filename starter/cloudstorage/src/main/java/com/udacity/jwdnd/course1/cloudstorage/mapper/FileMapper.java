package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileCustom;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    ArrayList<FileCustom> getFilesByUser(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileCustom getFileByFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    FileCustom getFileByName(String filename);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = userid")
    FileCustom getFileByNameAndUser(String filename, Integer userid);


    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileCustom file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);


}
