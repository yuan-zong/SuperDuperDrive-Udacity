package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, FileForm fileForm, CredentialForm credentialForm, Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("filesFromUser", fileService.getFilesByUser(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentialsFromUser", credentialService.getCredentialsByUser(user.getUserId()));
        return "home";
    }

}