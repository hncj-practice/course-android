package com.example.learningassistance.entity;

public class CourseList {
    private String imgUrl;
    private String name;
    private String lecturer;
    private int cid;

    public CourseList(String imgUrl, String name, String lecturer) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.lecturer = lecturer;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
