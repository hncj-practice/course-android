package com.example.learningassistance.entity;

public class MineDetail {
//    图片的id,在Recourse目录下的
    private int imgId;
    private String name;
    private String detail;

    public MineDetail(int imgId, String name, String detail) {
        this.imgId = imgId;
        this.name = name;
        this.detail = detail;
    }

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }
}
