package com.example.goro.controller;

import com.example.goro.entiry.QuizRequest;
import com.example.goro.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {

    @Autowired private QuizService quizService;

    // ✅ Create quiz
    @PostMapping("/createWithQuestions")
    public ResponseEntity<?> createQuizWithQuestions(@RequestBody QuizRequest request) {
        return quizService.createQuizWithQuestions(request);
    }

    // ✅ Get all quizzes
    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    // ✅ Get quiz by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    // ✅ Update quiz
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @RequestBody QuizRequest request) {
        return quizService.updateQuiz(id, request);
    }

    // ✅ Delete quiz
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        return quizService.deleteQuiz(id);
    }
}
