package com.example.Project.SecurityConfig;

import com.example.Project.Dao.StudentRepository;
import com.example.Project.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student=this.studentRepository.getStudentByStudentName(username);
        if(student==null){
            throw new UsernameNotFoundException("User can not found");
        }
        CustomUserDetails customUserDetails=new CustomUserDetails(student);
        return customUserDetails;
    }
}
