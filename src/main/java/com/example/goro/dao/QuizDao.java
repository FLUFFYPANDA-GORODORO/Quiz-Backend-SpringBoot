package com.example.goro.dao;

import com.example.goro.entiry.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCategory(String category);
}
