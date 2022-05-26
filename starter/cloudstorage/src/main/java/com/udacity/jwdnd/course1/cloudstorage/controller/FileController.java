package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/files")
public class FileController {
    private UserService userService;
    private FileService fileService;


    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public RedirectView uploadFile(Authentication authentication, FileForm fileForm, Model model, RedirectAttributes ra) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        int uploadResult = FileService.ERROR_GENERAL;
        RedirectView rv = new RedirectView("/home", true);
        ra.addFlashAttribute("attemptFileUpload", true);
        ra.addFlashAttribute("attemptFileDelete", false);

        try {
            uploadResult = fileService.uploadFile(fileForm.getFile(), user);
        } catch (FileNotFoundException e) {

            ra.addFlashAttribute("uploadFailedMessage", "The file cannot be found.");
            ra.addFlashAttribute("uploadSucceeded", false);
            return rv;
        } catch (IOException e) {
            ra.addFlashAttribute("uploadFailedMessage", "Upload failed: " + e.getMessage());
            ra.addFlashAttribute("uploadSucceeded", false);
            return rv;
        }
        if (uploadResult > 0) {
            ra.addFlashAttribute("uploadSuccessfulMessage", "Upload successfully completed");
            ra.addFlashAttribute("uploadSucceeded", true);
        } else if (uploadResult == fileService.ERROR_NO_FILE_SELECTED) {
            ra.addFlashAttribute("uploadFailedMessage", "No file selected!");
            ra.addFlashAttribute("uploadSucceeded", false);
        } else if (uploadResult == fileService.ERROR_FILE_ALREADY_EXISTED) {
            ra.addFlashAttribute("uploadFailedMessage", "Upload failed because a file with the same name already existed.");
            ra.addFlashAttribute("uploadSucceeded", false);
        } else {
            ra.addFlashAttribute("uploadFailedMessage", "Upload failed.");
            ra.addFlashAttribute("uploadSucceeded", false);
        }
        model.addAttribute("filesFromUser", this.fileService.getFilesByUser(user.getUserId()));
        return rv;
    }

    @RequestMapping("/delete/{fileId}")
    public RedirectView deleteFile(Authentication authentication, @PathVariable Integer fileId, Model model, RedirectAttributes ra) {

        String username = authentication.getName();
        User user = userService.getUser(username);
        FileCustom fileExisted = fileService.getFileByFileId(fileId);
        RedirectView rv = new RedirectView("/home", true);
        ra.addFlashAttribute("attemptFileUpload", false);
        ra.addFlashAttribute("attemptFileAccess", true);
        if (fileExisted == null){
            ra.addFlashAttribute("fileOperationSucceeded", false);
            ra.addFlashAttribute("fileStatusMessage", "The item doesn't exist!");
        } else {
            if (fileExisted.getUserid() == user.getUserId()) {
                fileService.deleteFile(fileId);
                ra.addFlashAttribute("fileOperationSucceeded", true);
                ra.addFlashAttribute("fileStatusMessage", "File deleted.");
            } else {
                ra.addFlashAttribute("fileOperationSucceeded", false);
                ra.addFlashAttribute("fileStatusMessage", "You have no access to this item!");
            }
        }


        return rv;
    }

    @RequestMapping("/download/{fileId}")
    public Object downloadFile(HttpServletResponse response, Authentication authentication, @PathVariable Integer fileId,
                                     Model model, RedirectAttributes ra) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        FileCustom fileExisted = fileService.getFileByFileId(fileId);
        RedirectView rv = new RedirectView("/home", true);
        ra.addFlashAttribute("attemptFileAccess", true);
        ra.addFlashAttribute("attemptFileUpload", false);
        if (fileExisted == null){
            ra.addFlashAttribute("fileOperationSucceeded", false);
            ra.addFlashAttribute("fileStatusMessage", "The item doesn't exist!");
        } else {
            if (fileExisted.getUserid() == user.getUserId()) {
                model.addAttribute("attemptFileAccess", true);
                model.addAttribute("fileStatusMessage", "");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileExisted.getFilename() + "\"")
                        .body(fileExisted.getFiledata());
            } else {
                ra.addFlashAttribute("fileOperationSucceeded", false);
                ra.addFlashAttribute("fileStatusMessage", "You have no access to this item!");
            }
        }
        return rv;
    }

}
