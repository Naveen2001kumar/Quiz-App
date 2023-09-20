package com.naveen.QuizApp.controller;

import com.naveen.QuizApp.dao.CustomerDao;
import com.naveen.QuizApp.entity.Result;
import com.naveen.QuizApp.model.QuestionForm;
import com.naveen.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.naveen.QuizApp.entity.Member;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CustomerDao custRepo;
    @Autowired
    Result result;
    String head;
String god;
    @GetMapping("/")
    public String welcome()
    {
        return "index.html";
    }
    @GetMapping ("register")
    public String register()
    {
        return "RegistrationForm";
    }
    @PostMapping("/registration")
    public String registeration(Member member,@RequestParam String userid , @RequestParam String password,Model model)
    {

        if (isValidUser(userid, password)) {
            model.addAttribute("error","Already Registered ");
        }
        else
            custRepo.save(member);
        System.out.println(member.toString());
           model.addAttribute("succes","Registerd succesfully login below!");
            return "RegistrationForm";
    }
    @ModelAttribute("result")
    public Result getResult() {
        return result;
    }
    Boolean submitted=false;
    @PostMapping("/quizCheck")
    public String setQuiz(@RequestParam String username , @RequestParam String password , Model model){
        if (isValidUser(username, password)) {
            submitted = false;
            Member mem=  custRepo.findByUserid(username);
            head = mem.getName();
            result.setUsername(head);
            QuestionForm qform= questionService.getQuestions();
            model.addAttribute("qForm",qform);
            String h = "Welcome ";
            model.addAttribute("name",h+head);
            return "quiz";
        } else {

            model.addAttribute("warning", "Invalid username or password");
            return "index";
        }
    }
    @PostMapping("/submit")
    public String submit(@ModelAttribute QuestionForm qForm ,Model model)
    {
        if(!submitted) {
            result.setTotalCorrect(questionService.getResult(qForm));
            questionService.saveScore(result);
            submitted = true;

        }
        return "result";
    }
    @GetMapping("/score")
    public String score(Model m) {
        List<Result> sList = questionService.getTopScore();
        m.addAttribute("sList", sList);
        return "scoreboard.html";
    }
    private boolean isValidUser(String username, String password) {

        return custRepo.existsByUserid(username) && custRepo.existsByPassword(password);
    }

}
