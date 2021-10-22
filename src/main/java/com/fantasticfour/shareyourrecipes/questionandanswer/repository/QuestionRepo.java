package com.fantasticfour.shareyourrecipes.questionandanswer.repository;

import com.fantasticfour.shareyourrecipes.domains.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    
}
