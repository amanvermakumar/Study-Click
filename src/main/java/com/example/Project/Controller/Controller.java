package com.example.Project.Controller;

import com.example.Project.Dao.StudentRepository;
import com.example.Project.Dao.UploadSubjectRepository;
import com.example.Project.Entity.Student;
import com.example.Project.Entity.UploadSubject;
import com.example.Project.SecurityConfig.SecuityConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    SecuityConfig secuityConfig;
    @Autowired

   private StudentRepository studentRepository;
    @Autowired
    private UploadSubjectRepository uploadSubjectRepository;

    @GetMapping("/home")

    public String home(Model m){
        m.addAttribute("title","Home");
        return "new-home";
    }

@GetMapping("/about")
    public String about(Model m){
    m.addAttribute("title","About");
        return "about";
    }
    @GetMapping("/courses")
    public String courses(Model m){
        m.addAttribute("title","Courses");
        return "courses";
    }
    @GetMapping("/contact")
    public String contact(Model m){
        m.addAttribute("title","Contact");
        return "contact";
    }
    @GetMapping("/login")
    public String login(Model m){

        m.addAttribute("title","Login");
        return "login";
    }
    @GetMapping("/signUp")
    public String signUp(Model m){
        m.addAttribute("title","SingUp");
        m.addAttribute("student",new Student());
        return "signUp";
    }
    @PostMapping("/do-register")
    public String register(@Valid @ModelAttribute("student") Student student,BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("profileImage") MultipartFile file, @RequestParam(value = "agree",defaultValue ="false") boolean agree , Model m) {
        try {
            if (!agree) {
                m.addAttribute("student", student);
                System.out.println("check");
                redirectAttributes.addFlashAttribute("success", "Register Successfully !....!!...");
                return "Signup";
            } else {
                if (bindingResult.hasErrors()) {
                    m.addAttribute("student", student);
                    return "Signup";
                }

                student.setRole("ROLE_USER");
                student.setPassword(secuityConfig.passwordEncoder().encode(student.getPassword()));
                if (file.isEmpty()) {
                    student.setImage("defaultProfile.png");
                } else {
//                student.setImage(file.getOriginalFilename());
//                File file1 = new ClassPathResource("static/image").getFile();
//                Path path= Paths.get(file1.getAbsolutePath()+File.separator+file.getOriginalFilename());
//                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
//                System.out.println("File is uploaded");
                    String fileName = file.getOriginalFilename();
                    student.setImage(fileName);
                    student.setData(file.getBytes());
                }
                System.out.println(student);
                Student result = this.studentRepository.save(student);

                redirectAttributes.addFlashAttribute("success", "Register Successfully !....!!...");


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/signUp";
    }
    @GetMapping("/stImage/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getDocument(@PathVariable Integer id) {
        Optional<Student> documentOptional =studentRepository.findById(id);
        if (!documentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Student document = documentOptional.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(document.getName()).build());
        return new ResponseEntity<>(document.getData(), headers, HttpStatus.OK);
    }
    @GetMapping("/null")
    public String fail(){
        return "Null";
    }
//    @GetMapping("/error")
//    public String chart(){
//        return "error";
//    }
    @GetMapping("/courseProgramming/{cid}")
public String courseProgramming (Model m,@PathVariable("cid") int id){
       List<UploadSubject> subject= (List<UploadSubject>) uploadSubjectRepository.findAll();
        int count=subject.size();
        m.addAttribute("subject",subject);
        if(id==1) {
            return "CourseProgramming";
        } else if (id==2) {
            return "Course-course";
        }
        else{
            return "Course-dataStructure";
        }
    }
}
