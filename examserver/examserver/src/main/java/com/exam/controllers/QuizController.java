package com.exam.controllers;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;


    //add Quiz

    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz)
    {
        Quiz quiz1 = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(quiz1);
    }

    // get Quiz

    @GetMapping("/{qId}")
    public Quiz getQuiz(@PathVariable("qId") Long qId)
    {
        return this.quizService.getQuiz(qId);
    }

    // get all quizzes

    @GetMapping("/")
    public ResponseEntity<?> getQuizzes()
    {
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    // Update Quiz

    @PutMapping("/")
    public Quiz updateQuiz(@RequestBody Quiz quiz)
    {
        return this.quizService.updateQuiz(quiz);
    }


    // Delete Quiz

    @DeleteMapping("/{qId}")
    public void deleteQuiz(@PathVariable("qId") Long qId)
    {
        this.quizService.deleteQuiz(qId);
    }


    @GetMapping("/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid)
    {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesOfCategory(category);
    }

    //get active Quizzes

    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes()
    {
        return this.quizService.getActiveQuizzes();
    }

    //get active Quizzes by category

    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzesByCategory(@PathVariable("cid") Long cid)
    {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }

}
