package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String notetitle;
    private String notedescription;
    private Integer noteid;

    public NoteForm(String notetitle, String notedescription, Integer noteid) {
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }
}
