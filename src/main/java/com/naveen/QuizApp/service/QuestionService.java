package com.naveen.QuizApp.service;
import com.naveen.QuizApp.dao.CustomerDao;
import com.naveen.QuizApp.dao.QuestionDao;
import com.naveen.QuizApp.entity.Question;
import com.naveen.QuizApp.entity.Member;
import com.naveen.QuizApp.model.QuestionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.naveen.QuizApp.dao.ResultRepo;
import com.naveen.QuizApp.entity.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuestionForm qform;
    @Autowired
    CustomerDao cusDao;
    @Autowired
    Member mem;
    @Autowired
    Result result;
    @Autowired
    ResultRepo rRepo;

    public  String addQuestion(Question question) {
        questionDao.save(question);
        return "Succes!";
    }
    public QuestionForm getQuestions(String category)
    {
        List<Question> allQues = questionDao.findByCategory(category);
        List<Question> qList = new ArrayList<>();

        Random random = new Random();

        for(int i=0; i<10; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }
        qform.setQuestion(qList);
       //qform.setQuestion(allQues);
        return qform;
    }
    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for(Question q: qForm.getQuestion())
            if(q.getRightAnswer().equals(q.getChose())){
                correct++;
            }
        return correct;
    }
    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUsername(result.getUsername());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        rRepo.save(saveResult);
    }

    public List<Result> getTopScore() {
        List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        return sList;
    }


    public List<Question> getAllQuestions()
     {
         return questionDao.findAll();
     }

    public List<Question> getQuestionByCategory(String category) {
        return questionDao.findByCategory(category);

    }

}
