package com.example.learningassistance.entity;

public class Opinion {
    private int imgId;
    private String name;
    private String time;
    private String content;

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public Opinion(int imgId, String name, String time, String content) {
        this.imgId = imgId;
        this.name = name;
        this.time = time;
        this.content = content;
    }
}
