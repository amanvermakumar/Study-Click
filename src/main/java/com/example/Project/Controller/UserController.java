package com.example.Project.Controller;

import com.example.Project.Dao.*;
import com.example.Project.Entity.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    List<Integer> nextId=new ArrayList<>();
    private HashMap<Integer,String> selectAnswer=new HashMap<>();
    private int quizScore;
    String resultStatus = null;String performance=null;double percentage=0.0; float totalScore= 0.0F;
    @Autowired
   private StudentRepository studentRepository;
    @Autowired
    private CategoryRepository categoryRepository;
  @Autowired
  private UploadSubjectRepository uploadSubjectRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizResultRepository quizResultRepository;
    @GetMapping("/dologin")
    public String dologin(Principal principal, Model m)
    {
        int totalMarks = 0,totalScore = 0;
                double performance=0;
     String name=principal.getName();
     Student student=this.studentRepository.getStudentByStudentName(name);
     List<Result> result=student.getResults();
     if(!result.isEmpty()) {
         for (Result re : result) {
         totalMarks += Integer.parseInt(quizRepository.findById(re.getQuizId()).get().getMaxMarks());
         totalScore += re.getScoreMarks();
         }
         performance=(totalScore*100)/totalMarks;
     }
     else{
         performance=0;
     }

        m.addAttribute("result",result);
        m.addAttribute("totalTest",result.size());
        m.addAttribute("performance",performance);
        return "user/dologin";
    }
    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String username=principal.getName();
        Student student=this.studentRepository.getStudentByStudentName(username);
        model.addAttribute("student",student);
    }
    @GetMapping("/profile")
    public String profile(){
        return "user/profile";
    }
    @GetMapping("/course")
    public String showCourse(){
        return "user/course";
    }
    @GetMapping("/course-programming/{cid}")
    public String showCourseProgramming(Model m,@PathVariable ("cid") int id)
    {
//        List<UploadSubject>subject= (List<UploadSubject>) uploadSubjectRepository.findAll();
       List<UploadSubject> subject= uploadSubjectRepository.findAll();
        int count=subject.size();
        m.addAttribute("subject",subject);
        if(id==1){
            return "user/course-programming";
        }
        else if(id==2){
            return "user/course-course";
        }
        else{
            return "user/course-dataStructure";
        }
    }
    @GetMapping("/viewContent/{id}")
    public String getPdfContentById(@PathVariable Integer id, HttpServletResponse response, Model m) throws IOException {
        Optional<UploadSubject> pdfOptional = uploadSubjectRepository.findById(id);
        String name=pdfOptional.get().getSubjectName();
        m.addAttribute("text",pdfOptional.get().getContent());
        return "testHtml";
    }

    @GetMapping("/test")
    public String test(Model m){
        List<Category> all = this.categoryRepository.findAll();
        int count= (int) all.stream().count();
        m.addAttribute("count",count);
        m.addAttribute("all",all);
        return "user/test";
    }
  @GetMapping("/showQuiz/{cid}")
  public String showQuiz(Model m,@PathVariable("cid") int id){
      Category category=this.categoryRepository.findById(id).get();
      Set<Quiz> quizzes = category.getQuizzes();
      List<Quiz> quizList=new ArrayList<>();
      for (Quiz q:quizzes){
          if(q.isActive()){
             quizList.add(q);
          }
      }
      Collections.shuffle(quizList);
      int s= (int) quizzes.stream().count();
      m.addAttribute("count",s);
      m.addAttribute("all",quizList);
        return "user/showQuiz";
  }
  @GetMapping("/startQuiz/{cid}")
    public String startQuiz(Model m,@PathVariable("cid") int id){
      resultStatus = null; performance=null; percentage=0.0;  totalScore=0.0f;
      Quiz quiz = this.quizRepository.findById(id).get();
      List<Question> questions = quiz.getQuestions();
      quizScore=0;
      nextId.removeAll(nextId);
      selectAnswer.clear();
      for (Question q:questions){
          nextId.add(q.getQuesId());
      }
      int question= Integer.parseInt(quiz.getNumberOfQuestions());
      int maxMarks= Integer.parseInt(quiz.getMaxMarks());
      m.addAttribute("quizTitle",quiz.getTitle());
      m.addAttribute("question",question);
      m.addAttribute("maxMarks",maxMarks);
      m.addAttribute("quesId",quiz.getqId());
      int marks=maxMarks/question;
      m.addAttribute("marks",marks);
      m.addAttribute("quizId",id);
      return "user/startQuiz";
  }
  @GetMapping("/quizQuestion/{quizId}")
    public String quizQuestion(Model m,@PathVariable("quizId") int id){
        try{
            Quiz quiz = this.quizRepository.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int question= Integer.parseInt(quiz.getNumberOfQuestions());
            int maxMarks= Integer.parseInt(quiz.getMaxMarks());
            Question question1 = questions.get(0);
            int marks=maxMarks/question;
            m.addAttribute("quizTitle",quiz.getTitle());
            m.addAttribute("question",question);
            m.addAttribute("maxMarks",maxMarks);
            m.addAttribute("quesId",quiz.getqId());
            m.addAttribute("marks",marks);
            m.addAttribute("checked",true);
            m.addAttribute("start",question1.getQuesId());
            return "user/startQuiz";
        } catch (Exception e){
           return "about" ;
        }
  }
  @PostMapping("/quizData/{quesId}")
    public String data(@PathVariable("quesId") int id,@ModelAttribute("data") Question question,@RequestParam("action") String action,Model m,Principal principal) {
      int index = 0;
      for (int i = 0; i < nextId.size(); i++) {
          if (nextId.get(i) == id) {
              index = i;
          }
      }
      selectAnswer.put(id, question.getContent());

      if (action.equals("Submit & Next")) {
          return "redirect:/user/previousQuestion/" + index + "/" + 5;
      }
      else if (action.equals("End Test")) {
          Map<String, Integer> graphData = new TreeMap<>();
         Question question1 = this.questionRepository.findById(id).get();
          m.addAttribute("quizId", question1.getQuiz().getqId());
          m.addAttribute("totalQuestion", nextId.size());
          m.addAttribute("attemptedQuestion", selectAnswer.size());
          m.addAttribute("saveResultId",id);
          for (Integer entry : selectAnswer.keySet()) {
              if (selectAnswer.get(entry).equals(this.questionRepository.findById(entry).get().getAnswer())) {
                  quizScore++;
              }
              m.addAttribute("correctAnswer", quizScore);
              m.addAttribute("wrongAnswer", selectAnswer.size() - quizScore);
          }
          if (selectAnswer.size() == quizScore) {
              graphData.put("Attempted Question", selectAnswer.size() - quizScore);
          } else if(selectAnswer.size() == selectAnswer.size() - quizScore){
              m.addAttribute("Attempted Question",selectAnswer.size() - quizScore);
          }
          else  {
              graphData.put("Attempted Question", selectAnswer.size());
          }
          graphData.put("Correct Answer", quizScore);
          graphData.put("Unattempted Question", nextId.size() - selectAnswer.size());
          graphData.put("Incorrect Question", selectAnswer.size() - quizScore);
          int maxMarks = Integer.parseInt(question1.getQuiz().getMaxMarks());
          float makrs = (float) maxMarks / nextId.size();
           totalScore = quizScore * makrs;
           percentage = (totalScore / maxMarks) * 100;
          if (percentage <= 33) {
             resultStatus="Fail";
              performance="Bad";
          } else if (percentage> 33 && percentage <= 70) {
              resultStatus="Pass";
              performance="Moderate";

          } else {
              resultStatus="Pass";
              performance="Very Good";
          }
          m.addAttribute("performance", performance);
          m.addAttribute("resultStatus", resultStatus);
          m.addAttribute("percentage", percentage);
          m.addAttribute("score", totalScore);
          m.addAttribute("totalMarks", maxMarks);
          m.addAttribute("chartData", graphData);
          m.addAttribute("result", new Result());
          return "user/quizResult";
      }
      else {
          String username = principal.getName();
          Result result=new Result();
          Student student = this.studentRepository.getStudentByStudentName(username);
          result.setQuizId(this.questionRepository.findById(id).get().getQuiz().getqId());
          result.setQuizName(this.questionRepository.findById(id).get().getQuiz().getTitle());
          result.setResult(resultStatus);
          result.setTotalMarks(Integer.parseInt(this.questionRepository.findById(id).get().getQuiz().getMaxMarks()));
          result.setDate(LocalDate.now());
          result.setPerformance(performance);
          result.setPercentage(percentage);
          result.setCorrectQuestion(quizScore);
          result.setWrongQuestion(selectAnswer.size() - quizScore);
          result.setTotalQuestion(nextId.size());
          result.setScoreMarks(totalScore);
          result.setStudent(student);
          result.setAttemptedQuestion(selectAnswer.size());
          this.quizResultRepository.save(result);
          return "redirect:/user/test";
      }

  }
  @GetMapping("/startQuestion/{quesId}")
    public String startQuestion(Model m,@PathVariable("quesId")int id){
      Question question = this.questionRepository.findById(id).get();
      int quizId=question.getQuiz().getqId();
      Quiz quiz = this.quizRepository.findById(quizId).get();
      List<Question> questionList = quiz.getQuestions();
      m.addAttribute("quesId",id);
      m.addAttribute("data",new Question());
      m.addAttribute("option1",question.getOption1());
      m.addAttribute("questionTitle",question.getContent());
      m.addAttribute("option2",question.getOption2());
      m.addAttribute("option3",question.getOption3());
      m.addAttribute("option4",question.getOption4());
      m.addAttribute("questionNo",questionList.indexOf(question));
      m.addAttribute("lastindex",nextId.get(nextId.size()-1));
      m.addAttribute("firstindex",nextId.get(0));
      m.addAttribute("selectedAnswer",selectAnswer.get(id));
      m.addAttribute("quizTitle",quiz.getTitle());
      m.addAttribute("totalQuestion",questionList.size());
      m.addAttribute("attemptedQuestion",selectAnswer.size());
      m.addAttribute("remainingQuestion",questionList.size()-selectAnswer.size());
      int maxMarks= Integer.parseInt(quiz.getMaxMarks());
      float marks= (float) maxMarks / questionList.size();
      m.addAttribute("marks",marks);
      m.addAttribute("color",selectAnswer.keySet());
      m.addAttribute("questionList",questionList);
      return "user/displayQuestion";
  }
  @GetMapping("/previousQuestion/{index}/{testId}")
    public String previousQuestion(@PathVariable("index") int index,@PathVariable("testId") int testId) throws ArrayIndexOutOfBoundsException{
        int id;
        if(testId==5){
             id=nextId.get(index+1);
            return "redirect:/user/startQuestion/"+id;
        }
        else{
             id=nextId.get(index-1);
            return "redirect:/user/startQuestion/"+id;
        }
  }
    @GetMapping("/settings")
    public String settings(){

        return "user/settings";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword") String newPassword, Principal principal, RedirectAttributes redirectAttributes){

        String username=principal.getName();
       Student student= studentRepository.getStudentByStudentName(username);
        String password=student.getPassword();
        if (this.bCryptPasswordEncoder.matches(oldPassword,password)){
            student.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.studentRepository.save(student);
            redirectAttributes.addFlashAttribute("success", "Password Change Successfully !....!!...");
        }
        else {
            redirectAttributes.addFlashAttribute("success", "Please Enter Correct Old Password !....!!...");
        }

        return "redirect:/user/settings";

    }
}


