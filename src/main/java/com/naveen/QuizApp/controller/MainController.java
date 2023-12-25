package com.naveen.QuizApp.controller;

import com.naveen.QuizApp.dao.CustomerDao;
import com.naveen.QuizApp.dao.QuestionDao;
import com.naveen.QuizApp.entity.Question;
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
    @Autowired
    QuestionDao questionDao;
    String head;

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
           model.addAttribute("succes","Registerd succesfully login below!");
            return "RegistrationForm";
    }
    @GetMapping("addQuestion")
    public String setQuestion()
    {
        return "add";
    }
    @PostMapping("add")
    public void addQuestion(Question question , Model model )
    {
        questionDao.save(question);
        model.addAttribute("succes","One Question added Succesfully");
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
//            QuestionForm qform = questionService.getQuestions();
//            model.addAttribute("qForm",qform);
            String h = "Welcome ";
            model.addAttribute("name",h+head);
            return "home";
        } else {

            model.addAttribute("warning", "Invalid username or password");
            return "index";
        }
    }
    @GetMapping("category/{category}")
    public String setQuiz(@RequestParam("category") String category , Model model)
    {
        submitted = true;
        QuestionForm qform = questionService.getQuestions(category);
        model.addAttribute("qForm",qform);
        String h = "Welcome ";
        model.addAttribute("name",h+head);
        return "quiz";
    }

    @PostMapping("category/submit")
    public String submit(@ModelAttribute QuestionForm qForm ,Model model)
    {
        if(submitted) {
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
    @GetMapping("/home")
    public String gethome()
    {
        return "home";
    }
    private boolean isValidUser(String username, String password) {

        return custRepo.existsByUserid(username) && custRepo.existsByPassword(password);
    }

//    private boolean isValidUser(String username, String password) {
//
//        return custRepo.existsByUserid(username) && custRepo.existsByPassword(password);
//    }

}
