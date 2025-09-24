package com.example.goro.service;

import com.example.goro.dao.StudentQuizAttemptDao;
import com.example.goro.entiry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StudentQuizAttemptService {

    @Autowired
    private StudentQuizAttemptDao attemptDao;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private QuizService quizService;

    public ResponseEntity<?> startQuiz(Long quizId, String username) {
        User student = userDetailsService.getUserByUsername(username);
        Optional<Quiz> quizOpt = quizService.getQuizByIdInternal(quizId);
        if (quizOpt.isEmpty()) return ResponseEntity.badRequest().body("Quiz not found");

        Quiz quiz = quizOpt.get();
        StudentQuizAttempt attempt = new StudentQuizAttempt();
        attempt.setStudent(student);
        attempt.setQuiz(quiz);
        attempt.setCompleted(false);
        attempt.setScore(0);
        attempt.setSelectedAnswers(null);

        attemptDao.save(attempt);
        return ResponseEntity.ok(quiz);
    }

    public ResponseEntity<?> submitQuiz(Long attemptId, java.util.List<String> selectedAnswers) {
        Optional<StudentQuizAttempt> attemptOpt = attemptDao.findById(attemptId);
        if (attemptOpt.isEmpty()) return ResponseEntity.badRequest().body("Attempt not found");

        StudentQuizAttempt attempt = attemptOpt.get();
        Quiz quiz = attempt.getQuiz();

        int score = 0;
        if (quiz.getQuestions() != null) {
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                if (selectedAnswers.size() > i && selectedAnswers.get(i) != null &&
                        selectedAnswers.get(i).equals(quiz.getQuestions().get(i).getRightAnswer())) {
                    score++;
                }
            }
        }

        attempt.setSelectedAnswers(selectedAnswers);
        attempt.setScore(score);
        attempt.setCompleted(true);

        attemptDao.save(attempt);
        return ResponseEntity.ok(attempt);
    }
}
