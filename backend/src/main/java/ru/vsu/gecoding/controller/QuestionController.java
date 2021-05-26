package ru.vsu.gecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.gecoding.data.entity.Question;
import ru.vsu.gecoding.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getQuestions(){
        return questionService.getAllQuestions();
    }

    @PostMapping(path = "")
    public void setUserQuestions(@RequestBody ArrayList<String> questions, Authentication authentication){
        String currentPrincipalName = authentication.getName();
        questionService.setUserQuestions(questions, currentPrincipalName);
    }
}
