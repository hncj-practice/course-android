package com.example.learningassistance.entity;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private String commentId;
    private String username;
    private String imgUrl;
    private String content;
    private String time;
    //这条评论是回复谁的
    private String replyName = null;
    //谁回复了这条评论
    private List<String> replyList;

    public Comment(String commentId, String username, String imgUrl, String content, String time) {
        this.commentId = commentId;
        this.username = username;
        this.imgUrl = imgUrl;
        this.content = content;
        this.time = time;
        this.replyList = new ArrayList<>();
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUsername() {
        return username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public List<String> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<String> replyList) {
        this.replyList = replyList;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }
}
