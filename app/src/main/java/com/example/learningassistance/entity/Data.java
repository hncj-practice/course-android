package com.example.learningassistance.entity;

import com.example.learningassistance.utils.FileUtils;

import java.io.Serializable;

public class Data implements Serializable {
    private String name;
    private String url;
    private String localPath;
    private int type;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public int getType() {
        return type;
    }

    public Data(String url, int type, String localPath) {
        this.url = url;
        this.type = type;
        this.localPath = localPath;
        this.name = FileUtils.getFileName(url);

    }
}
