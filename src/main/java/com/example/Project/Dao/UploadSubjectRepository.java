package com.example.Project.Dao;

import com.example.Project.Entity.UploadSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadSubjectRepository extends JpaRepository<UploadSubject,Integer> {
}
