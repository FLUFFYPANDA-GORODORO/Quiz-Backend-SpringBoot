package com.example.goro.service;

import com.example.goro.dao.AttemptDao;
import com.example.goro.dao.QuizDao;
import com.example.goro.entiry.Attempt;
import com.example.goro.entiry.Question;
import com.example.goro.entiry.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AttemptService {

    @Autowired
    private AttemptDao attemptDao;

    @Autowired
    private QuizDao quizDao;

    public Attempt submitAttempt(Long quizId, List<String> answers) {
        Optional<Quiz> quizOpt = quizDao.findById(quizId);
        if (quizOpt.isEmpty()) {
            throw new RuntimeException("Quiz not found");
        }

        Quiz quiz = quizOpt.get();
        List<Question> questions = quiz.getQuestions();

        // Calculate score
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            if (i < answers.size() && q.getRightAnswer().equals(answers.get(i))) {
                score++;
            }
        }

        // Save attempt
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setUsername(username);
        attempt.setScore(score);
        attempt.setTotalQuestions(questions.size());

        return attemptDao.save(attempt);
    }
}
