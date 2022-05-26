package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private CredentialService credentialService;
    private NoteService noteService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, FileForm fileForm, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("filesFromUser", fileService.getFilesByUser(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentialsFromUser", credentialService.getCredentialsByUser(user.getUserId()));
        model.addAttribute("notesFromUser", noteService.getNotesByUser(user.getUserId()));
        return "home";
    }

}