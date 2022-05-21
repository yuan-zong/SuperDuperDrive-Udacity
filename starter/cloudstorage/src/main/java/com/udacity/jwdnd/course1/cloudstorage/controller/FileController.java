package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;

@Controller
@RequestMapping("/files")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
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
        RedirectView rv = new RedirectView("/result", true);

        try {
            uploadResult = fileService.uploadFile(fileForm.getFile(), user);
        } catch (FileNotFoundException e) {

            ra.addFlashAttribute("uploadFailedMessage", "The file cannot be found.");
            ra.addFlashAttribute("uploadSucceeded", false);
            return rv;
        } catch (IOException e) {
            ra.addFlashAttribute("uploadFailedMessage", "Upload failed");
            ra.addFlashAttribute("uploadSucceeded", false);
            e.printStackTrace();
            return rv;
        }
        if (uploadResult == fileService.ERROR_FILE_ALREADY_EXISTED) {
            ra.addFlashAttribute("uploadFailedMessage", "Upload failed because a file with the same name already existed.");
            ra.addFlashAttribute("uploadSucceeded", false);
        } else {
            ra.addFlashAttribute("uploadSucceeded", true);
            rv.setUrl("/home");
            model.addAttribute("filesFromUser", this.fileService.getFilesByUser(user.getUserId()));
        }
        return rv;
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        return "redirect:/home";
    }

    @PostMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer fileId) {
        FileCustom file = fileService.getFileByFileId(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }
}
