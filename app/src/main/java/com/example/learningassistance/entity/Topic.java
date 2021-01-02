package com.example.learningassistance.entity;

import java.io.Serializable;

public class Topic implements Serializable {
    private String imgId;
    private String creator;
    private String time;
    private String title;
    private String question;

    private String topicId;

    public String getImgId() {
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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public Topic(String imgId, String creator, String time, String title, String question) {
        this.imgId = imgId;
        this.creator = creator;
        this.time = time;
        this.title = title;
        this.question = question;
    }
}
