package com.example.goro.entiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;
    private String questionTitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String rightAnswer;
    private String difficultyLevel;
}
