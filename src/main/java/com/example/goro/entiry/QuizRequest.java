package com.example.goro.entiry;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuizRequest {
    private String title;
    private String category;
    private List<Question> questions;
}
