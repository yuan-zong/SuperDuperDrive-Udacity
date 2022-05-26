package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public CredentialMapper getCredentialMapper() {
        return credentialMapper;
    }

    public void setCredentialMapper(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public ArrayList<CredentialCustom> getCredentialsByUser(Integer userid) {
        return credentialMapper.getCredentialsByUser(userid);
    }

    public CredentialCustom getCredentialByCredentialid(Integer credentialid) {
        return credentialMapper.getCredentialByCredentialId(credentialid);
    }

    public int insert(String url, String username, String password, User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(password, key);
        return credentialMapper.insert(new CredentialCustom(null, url,
                username, key, hashedPassword, user.getUserId()));
    }


    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredential(credentialid);
    }

    public void updateCredential(CredentialCustom credentialCustom) {
        credentialMapper.updateCredential(credentialCustom);
    }

    public void updateCredential(CredentialCustom credentialCustom, String url, String username, String password) {
        credentialCustom.setUrl(url);
        credentialCustom.setUsername(username);
        String hashedPassword = encryptionService.encryptValue(password, credentialCustom.getKey());
        credentialCustom.setPassword(hashedPassword);
        updateCredential(credentialCustom);
    }

}
