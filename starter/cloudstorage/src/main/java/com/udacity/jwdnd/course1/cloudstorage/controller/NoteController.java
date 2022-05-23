package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private UserService userService;
    private NoteService noteService;


    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/update")
    public RedirectView addOrUpdateNote(Authentication authentication, NoteForm noteForm, Model model, RedirectAttributes ra) {
        String username = authentication.getName();
        User user = userService.getUser(username);

        RedirectView rv = new RedirectView("/home", true);
        NoteCustom noteExisted = noteService.getNoteByNoteid(noteForm.getNoteid());
        if (noteExisted == null) {
            noteService.insert(noteForm.getNotetitle(), noteForm.getNotedescription(), user);
            ra.addFlashAttribute("noteChanged", true);
            ra.addFlashAttribute("noteChangedMessage", "Note successfully added");
        } else {
            noteService.updateNote(noteExisted, noteForm.getNotetitle(), noteForm.getNotedescription());
            ra.addFlashAttribute("noteChanged", true);
            ra.addFlashAttribute("noteChangedMessage", "Note successfully updated");
        }
        model.addAttribute("notesFromUser", noteService.getNotesByUser(user.getUserId()));
        return rv;
    }

    @RequestMapping("/delete/{noteid}")
    public RedirectView deleteNote(@PathVariable Integer noteid, Model model, RedirectAttributes ra) {
        noteService.deleteNote(noteid);
        RedirectView rv = new RedirectView("/home", true);
//        model.addAttribute("activeTab", "notes");
        ra.addFlashAttribute("noteChanged", true);
        ra.addFlashAttribute("noteChangedMessage", "Note successfully deleted");
        return rv;
    }
}
