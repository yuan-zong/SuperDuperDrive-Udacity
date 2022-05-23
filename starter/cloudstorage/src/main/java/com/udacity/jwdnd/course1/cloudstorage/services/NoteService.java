package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteCustom;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<NoteCustom> getNotesByUser(Integer userid) {
        return noteMapper.getNotesByUser(userid);
    }

    public NoteCustom getNoteByNoteid(Integer noteid) {
        return noteMapper.getNoteByNoteId(noteid);
    }

    public int insert(String notetitle, String notedescription, User user) {
        return noteMapper.insert(new NoteCustom(null, notetitle,
                notedescription, user.getUserId()));
    }


    public void deleteNote(Integer noteid) {
        noteMapper.deleteNote(noteid);
    }

    public void updateNote(NoteCustom NoteCustom) {
        noteMapper.updateNote(NoteCustom);
    }

    public void updateNote(NoteCustom NoteCustom, String notetitle, String notedescription) {
        NoteCustom.setNotetitle(notetitle);
        NoteCustom.setNotedescription(notedescription);
        updateNote(NoteCustom);
    }

}
