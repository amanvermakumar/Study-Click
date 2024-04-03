package com.example.Project.Controller;

import com.example.Project.Dao.QuestionRepository;
import com.example.Project.Dao.QuizRepository;
import com.example.Project.Entity.Question;
import com.example.Project.Entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class QuizController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;
    List<Integer> nextId=new ArrayList<>();
    private HashMap<Integer,String> selectAnswer=new HashMap<>();

    @GetMapping("/startQuestion/{quesId}")
    public String startQuestion(@org.jetbrains.annotations.NotNull Model m, @PathVariable("quesId")int id){
        Question question = this.questionRepository.findById(id).get();
        int quizId=question.getQuiz().getqId();
        Quiz quiz = this.quizRepository.findById(quizId).get();
        List<Question> questionList = quiz.getQuestions();
        m.addAttribute("quesId",id);
        m.addAttribute("data",new Question());
        m.addAttribute("questionNo",questionList.indexOf(question));
        m.addAttribute("lastindex",nextId.get(nextId.size()-1));
        m.addAttribute("firstindex",nextId.get(0));
        m.addAttribute("selectedAnswer",selectAnswer.get(id));
        m.addAttribute("quizTitle",quiz.getTitle());
        m.addAttribute("totalQuestion",questionList.size());
        m.addAttribute("attemptedQuestion",selectAnswer.size());
        return " List.of(question,questionList.indexOf(question),nextId.get(nextId.size()-1),nextId.get(0),selectAnswer.get(id)) ";
    }
}
