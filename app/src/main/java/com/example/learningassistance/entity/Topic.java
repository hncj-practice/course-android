package com.example.learningassistance.entity;

import java.io.Serializable;

public class Topic implements Serializable {
    private int imgId;
    private String creator;
    private String time;
    private String title;
    private String question;

    public int getImgId() {
        return imgId;
    }

    public String getCreator() {
        return creator;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public Topic(int imgId, String creator, String time, String title, String question) {
        this.imgId = imgId;
        this.creator = creator;
        this.time = time;
        this.title = title;
        this.question = question;
    }
}
