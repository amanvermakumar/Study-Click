package com.example.Project.Dao;

import com.example.Project.Entity.Question;
import com.example.Project.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Set<Question> findByQuiz(Quiz quiz);
}
