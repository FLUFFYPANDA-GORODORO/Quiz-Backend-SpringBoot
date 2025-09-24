package com.example.goro.controller;

import com.example.goro.entiry.Attempt;
import com.example.goro.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/attempt")
@CrossOrigin(origins = "http://localhost:5173")
public class AttemptController {

    @Autowired private AttemptService attemptService;

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<?> submitAttempt(@PathVariable Long quizId,
                                           @RequestBody List<String> answers) {
        Attempt attempt = attemptService.submitAttempt(quizId, answers);
        return ResponseEntity.ok(attempt);
    }
}
