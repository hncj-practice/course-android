package com.example.learningassistance.course;

import java.io.Serializable;
import java.util.List;

public class Dynamic implements Serializable {
    private String avatar;
    private String name;
    private String time;
    private String content;
    private List<String> images;

    public String getAvatar() {
        return avatar;
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

    public List<String> getImages() {
        return images;
    }

    public Dynamic(String avatar, String name, String time, String content, List<String> images) {
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.content = content;
        this.images = images;
    }
}
