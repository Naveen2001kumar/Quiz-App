package com.naveen.QuizApp.dao;

import com.naveen.QuizApp.service.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

}
