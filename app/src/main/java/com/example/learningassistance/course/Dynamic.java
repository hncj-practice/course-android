package com.example.learningassistance.course;

import java.util.List;

public class Dynamic {
    private String avatar;
    private String name;
    private String time;
    private String content;
    private String commentNum;
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

    public String getCommentNum() {
        return commentNum;
    }

    public List<String> getImages() {
        return images;
    }

    public Dynamic(String avatar, String name, String time, String content, String commentNum, List<String> images) {
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.content = content;
        this.commentNum = commentNum;
        this.images = images;
    }
}
