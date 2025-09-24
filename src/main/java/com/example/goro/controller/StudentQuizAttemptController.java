package com.example.goro.controller;

import com.example.goro.service.StudentQuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/studentAttempt")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentQuizAttemptController {

    @Autowired private StudentQuizAttemptService attemptService;

    @PostMapping("/start/{quizId}/{username}")
    public ResponseEntity<?> startQuiz(@PathVariable Long quizId, @PathVariable String username) {
        return attemptService.startQuiz(quizId, username);
    }

    @PostMapping("/submit/{attemptId}")
    public ResponseEntity<?> submitQuiz(@PathVariable Long attemptId, @RequestBody List<String> selectedAnswers) {
        return attemptService.submitQuiz(attemptId, selectedAnswers);
    }
}
