package com.example.goro.dao;

import com.example.goro.entiry.StudentQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentQuizAttemptDao extends JpaRepository<StudentQuizAttempt, Long> {
    // Optional: findByStudentId/ findByStudentAndQuiz...
}
