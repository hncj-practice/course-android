package com.example.learningassistance.entity;

public class ExamList {
    private String name;
    private String time;
    private String status;
    private String examId;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public ExamList(String name, String time, String status) {
        this.name = name;
        this.time = time;
        this.status = status;
    }
}
