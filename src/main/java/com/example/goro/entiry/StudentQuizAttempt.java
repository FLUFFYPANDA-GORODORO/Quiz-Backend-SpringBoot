package com.example.goro.entiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class StudentQuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // changed to Long for consistency

    @ManyToOne
    private User student;

    @ManyToOne
    private Quiz quiz;

    @ElementCollection
    private List<String> selectedAnswers;

    private int score;
    private boolean completed;
}
