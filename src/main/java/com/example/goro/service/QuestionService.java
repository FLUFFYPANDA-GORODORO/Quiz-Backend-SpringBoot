package com.example.goro.service;

import com.example.goro.dao.QuestionDao;
import com.example.goro.entiry.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Long id, Question updatedQuestion) {
        Optional<Question> q = questionDao.findById(id);
        if (q.isEmpty()) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        Question question = q.get();
        question.setCategory(updatedQuestion.getCategory());
        question.setQuestionTitle(updatedQuestion.getQuestionTitle());
        question.setOption1(updatedQuestion.getOption1());
        question.setOption2(updatedQuestion.getOption2());
        question.setOption3(updatedQuestion.getOption3());
        question.setOption4(updatedQuestion.getOption4());
        question.setRightAnswer(updatedQuestion.getRightAnswer());
        question.setDifficultyLevel(updatedQuestion.getDifficultyLevel());

        questionDao.save(question);
        return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        Optional<Question> q = questionDao.findById(id);
        if (q.isEmpty()) {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
        questionDao.deleteById(id);
        return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
    }
}
