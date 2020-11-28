package com.example.learningassistance.entity;

public class Opinion {
    private String imgUrl;
    private String name;
    private String time;
    private String content;

    public String getImgUrl() {
        return imgUrl;
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

    public Opinion(String imgUrl, String name, String time, String content) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.time = time;
        this.content = content;
    }
}
