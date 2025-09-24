package com.example.goro.entiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private String username;
    private int score;
    private int totalQuestions;

    public Attempt() {}

    public Attempt(Quiz quiz, String username, int score, int totalQuestions) {
        this.quiz = quiz;
        this.username = username;
        this.score = score;
        this.totalQuestions = totalQuestions;
    }
}
