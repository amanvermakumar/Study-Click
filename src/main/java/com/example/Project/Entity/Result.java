package com.example.Project.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Quiz_Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int resultId;
    private int totalQuestion;
    private int attemptedQuestion;
    private int correctQuestion;
    private int wrongQuestion;
    private double percentage;
    private String result;
    private String performance;
    private double scoreMarks;
    private int quizId;
    private String quizName;
    private int totalMarks;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getAttemptedQuestion() {
        return attemptedQuestion;
    }

    public void setAttemptedQuestion(int attemptedQuestion) {
        this.attemptedQuestion = attemptedQuestion;
    }

    public int getCorrectQuestion() {
        return correctQuestion;
    }

    public void setCorrectQuestion(int correctQuestion) {
        this.correctQuestion = correctQuestion;
    }

    public int getWrongQuestion() {
        return wrongQuestion;
    }

    public void setWrongQuestion(int wrongQuestion) {
        this.wrongQuestion = wrongQuestion;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public double getScoreMarks() {
        return scoreMarks;
    }

    public void setScoreMarks(double scoreMarks) {
        this.scoreMarks = scoreMarks;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
