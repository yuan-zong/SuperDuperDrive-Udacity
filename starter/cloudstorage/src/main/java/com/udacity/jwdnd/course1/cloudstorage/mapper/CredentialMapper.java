package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialCustom;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    ArrayList<CredentialCustom> getCredentialsByUser(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    CredentialCustom getCredentialByCredentialId(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(CredentialCustom credentialCustom);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredential(Integer credentialid);


    @Update("UPDATE CREDENTIALS " +
            "SET url = #{url}, username = #{username}, password = #{password} " +
            "WHERE credentialid = #{credentialid}")
    void updateCredential(CredentialCustom credentialCustom);


}
