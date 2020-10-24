package com.example.learningassistance.entity;

public class CourseList {
    private int imgId;
    private String name;
    private String lecturer;

    public CourseList(int imgId, String name, String lecturer) {
        this.imgId = imgId;
        this.name = name;
        this.lecturer = lecturer;
    }

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }

    public String getLecturer() {
        return lecturer;
    }
}
