package com.example.learningassistance.entity;

public class Option {
    private String name;
    private int imgId;

    public Option(String name, int imgId) {
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
