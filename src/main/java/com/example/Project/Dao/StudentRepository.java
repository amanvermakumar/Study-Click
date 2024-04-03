package com.example.Project.Dao;

import com.example.Project.Entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student,Integer> {
    @Query("select st from Student st where st.email= :email")
public Student getStudentByStudentName(@Param("email")String email);
}
