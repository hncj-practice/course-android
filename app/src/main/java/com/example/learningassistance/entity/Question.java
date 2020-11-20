package com.example.learningassistance.entity;

public class Question {
    String question;
    String answers;

    public String getQuestion() {
        return question;
    }

    public String getAnswers() {
        return answers;
    }

    public Question(String question, String answers) {
        this.question = question;
        this.answers = answers;
    }
}
