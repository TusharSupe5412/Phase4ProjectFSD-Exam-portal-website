package com.exam.controllers;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.services.QuestionService;
import com.exam.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;


    //add Question

    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question question1 = this.questionService.addQuestion(question);
        return ResponseEntity.ok(question1);
    }

    // get Question

    @GetMapping("/{questionId}")
    public Question getQuestion(@PathVariable("questionId") Long questionId) {
        return this.questionService.getQuestion(questionId);
    }

    // get all Questions of any quiz

    @GetMapping("/quiz/{qId}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qId") Long qId) {

//        Quiz quiz = new Quiz();
//        quiz.setqId(qId);
//        Set<Question> questionsOfQuiz= this.questionService.getQuestionsOfQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);

        Quiz quiz = this.quizService.getQuiz(qId);
        Set<Question> questions = quiz.getQuestion();
        List<Question> list = new ArrayList(questions);
        if (list.size() > Integer.parseInt(quiz.getNoOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNoOfQuestions() + 1));
        }

        list.forEach((q)->{
            q.setAnswer("");
        });
        Collections.shuffle(list);
        return ResponseEntity.ok(list);

    }


    @GetMapping("/quiz/all/{qId}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qId") Long qId) {

        Quiz quiz = new Quiz();
        quiz.setqId(qId);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

    }

    // Update Questions

    @PutMapping("/")
    public Question updateQuestion(@RequestBody Question question) {
        return this.questionService.updateQuestion(question);
    }


    // Delete Question

    @DeleteMapping("/{questionId}")
    public void deleteQuiz(@PathVariable("questionId") Long questionId) {
        this.questionService.deleteQuestion(questionId);
    }


//    eval quiz

    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;
        for (Question q : questions) {
                // single question
            Question question = this.questionService.get(q.getQuesId());
            if (question.getAnswer().equals(q.getGivenAnswer())) {
                // correct
                correctAnswers++;

                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                     marksGot+=marksSingle;
            }

            if(q.getGivenAnswer()!=null)
               {
                 attempted++;
               }
        };

        Map<String,Object> map = Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);


        return ResponseEntity.ok(map);
    }
}
