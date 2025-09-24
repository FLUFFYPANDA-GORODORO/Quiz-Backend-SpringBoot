package com.example.goro.dao;

import com.example.goro.entiry.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptDao extends JpaRepository<Attempt, Long> {
    // Optional: find attempts by username or quiz
}
