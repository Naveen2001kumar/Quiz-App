package com.naveen.QuizApp.dao;

import com.naveen.QuizApp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Member, Integer> {
    Boolean existsByUserid(String user);
    Boolean existsByPassword(String password);
    Member findByUserid(String userid);

}
