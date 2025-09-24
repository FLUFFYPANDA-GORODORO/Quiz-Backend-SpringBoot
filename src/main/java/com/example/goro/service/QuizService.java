package com.example.goro.service;

import com.example.goro.dao.QuizDao;
import com.example.goro.entiry.Quiz;
import com.example.goro.entiry.QuizRequest;
import com.example.goro.entiry.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    // ✅ Create Quiz with Questions
    public ResponseEntity<?> createQuizWithQuestions(QuizRequest request) {
        try {
            Quiz quiz = new Quiz();
            quiz.setTitle(request.getTitle());
            quiz.setCategory(request.getCategory());
            quiz.setQuestions(request.getQuestions());

            Quiz savedQuiz = quizDao.save(quiz);
            return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Get all quizzes
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        try {
            return new ResponseEntity<>(quizDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Get single quiz by ID
    public ResponseEntity<?> getQuizById(Long id) {
        Optional<Quiz> quizOpt = quizDao.findById(id);
        if (quizOpt.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quizOpt.get(), HttpStatus.OK);
    }

    // Internal method for services
    public Optional<Quiz> getQuizByIdInternal(Long id) {
        return quizDao.findById(id);
    }

    // ✅ Update Quiz
    public ResponseEntity<?> updateQuiz(Long id, QuizRequest request) {
        Optional<Quiz> quizOpt = quizDao.findById(id);
        if (quizOpt.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizOpt.get();
        quiz.setTitle(request.getTitle());
        quiz.setCategory(request.getCategory());

        List<Question> newQuestions = request.getQuestions();
        if (newQuestions != null && !newQuestions.isEmpty()) {
            quiz.setQuestions(newQuestions);
        }

        Quiz updatedQuiz = quizDao.save(quiz);
        return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
    }

    // ✅ Delete Quiz
    public ResponseEntity<?> deleteQuiz(Long id) {
        Optional<Quiz> quizOpt = quizDao.findById(id);
        if (quizOpt.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        quizDao.deleteById(id);
        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
    }
}
