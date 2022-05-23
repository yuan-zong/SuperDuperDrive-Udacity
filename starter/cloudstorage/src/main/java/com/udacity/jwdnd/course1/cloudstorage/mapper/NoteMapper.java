package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteCustom;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    ArrayList<NoteCustom> getNotesByUser(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    NoteCustom getNoteByNoteId(Integer noteid);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) " +
            "VALUES(#{noteid}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(NoteCustom noteCustom);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNote(Integer noteid);


    @Update("UPDATE NOTES " +
            "SET notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "WHERE noteid = #{noteid}")
    void updateNote(NoteCustom noteCustom);


}
