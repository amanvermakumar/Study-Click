package com.example.Project.Dao;

import com.example.Project.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizResultRepository extends JpaRepository<Result,Integer> {
}
