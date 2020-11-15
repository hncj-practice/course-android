package com.example.learningassistance.entity;

public class Member {
    private String name;
    private int imgId;

    public Member(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }
}
