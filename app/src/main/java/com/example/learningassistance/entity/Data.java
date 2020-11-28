package com.example.learningassistance.entity;

import com.example.learningassistance.utils.FileUtils;

import java.io.Serializable;

public class Data implements Serializable {
    private String name;
    private String url;
    private String type;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public Data(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }
}
