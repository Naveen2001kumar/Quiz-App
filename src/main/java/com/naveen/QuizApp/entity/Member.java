package com.naveen.QuizApp.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class member {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String userid;
    private String name;
    private String password;
    private int totalCorrect = 0;

}
