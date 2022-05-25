package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;


    public CredentialController(UserService userService, CredentialService fileService) {
        this.userService = userService;
        this.credentialService = fileService;
    }

    @PostMapping("/update")
    public RedirectView addOrUpdateCredential(Authentication authentication, CredentialForm credentialForm, Model model, RedirectAttributes ra) {
        String username = authentication.getName();
        User user = userService.getUser(username);

        RedirectView rv = new RedirectView("/home", true);
        CredentialCustom credentialExisted = credentialService.getCredentialByCredentialid(credentialForm.getCredentialid());
        ra.addFlashAttribute("attemptCredentialChange", true);
        if (credentialExisted == null) {
            credentialService.insert(credentialForm.getUrl(), credentialForm.getUsername(), credentialForm.getPassword(), user);
            ra.addFlashAttribute("credentialChanged", true);
            ra.addFlashAttribute("credentialStatusMessage", "Credential successfully added");
        } else {
            if (credentialExisted.getUserid() == user.getUserId()) {
                credentialService.updateCredential(credentialExisted, credentialForm.getUrl(), credentialForm.getUsername(), credentialForm.getPassword());
                ra.addFlashAttribute("credentialChanged", true);
                ra.addFlashAttribute("credentialStatusMessage", "Credential successfully updated");
            } else {
                ra.addFlashAttribute("credentialChanged", false);
                ra.addFlashAttribute("credentialStatusMessage", "You have no access to this item!");
            }
        }
        model.addAttribute("credentialsFromUser", credentialService.getCredentialsByUser(user.getUserId()));
        return rv;
    }

    @RequestMapping("/delete/{credentialid}")
    public RedirectView deleteCredential(Authentication authentication, @PathVariable Integer credentialid, Model model, RedirectAttributes ra) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        CredentialCustom credentialExisted = credentialService.getCredentialByCredentialid(credentialid);
        ra.addFlashAttribute("attemptCredentialChange", true);
        if (credentialExisted == null){
            ra.addFlashAttribute("credentialChanged", false);
            ra.addFlashAttribute("credentialStatusMessage", "The item doesn't exist!");
        } else {
            if (credentialExisted.getUserid() == user.getUserId()) {
                credentialService.deleteCredential(credentialid);
                ra.addFlashAttribute("credentialChanged", true);
                ra.addFlashAttribute("credentialChangedMessage", "Credential successfully deleted");
            } else {
                ra.addFlashAttribute("credentialChanged", false);
                ra.addFlashAttribute("credentialStatusMessage", "You have no access to this item!");
            }
        }

        RedirectView rv = new RedirectView("/home", true);
//        model.addAttribute("activeTab", "credentials");
        return rv;
    }
}
