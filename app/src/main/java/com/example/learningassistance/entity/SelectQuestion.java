package com.example.learningassistance.entity;

public class SelectQuestion {
    String question;
    String[] answers;

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public SelectQuestion(String question, String[] answers) {
        this.question = question;
        this.answers = answers;
    }
}
