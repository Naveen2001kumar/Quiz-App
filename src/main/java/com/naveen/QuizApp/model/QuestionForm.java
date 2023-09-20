package com.naveen.QuizApp.model;

import com.naveen.QuizApp.entity.Question;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class QuestionForm {

    private List<Question> question;

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
