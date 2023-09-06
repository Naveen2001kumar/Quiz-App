package com.naveen.QuizApp.service;
import com.naveen.QuizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionService {
    @Autowired
    QuestionDao questionDao;
     public List<Question> getAllQuestions()
     {
         return questionDao.findAll();
     }
}
