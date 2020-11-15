package com.example.learningassistance.entity;

import com.example.learningassistance.utils.FileUtils;

import java.io.Serializable;

public class Data implements Serializable {
    private String name;
    private String url;
    private int type;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    public Data(String url, int type) {
        this.url = url;
        this.type = type;
        this.name = FileUtils.getFileName(url);

    }
}
