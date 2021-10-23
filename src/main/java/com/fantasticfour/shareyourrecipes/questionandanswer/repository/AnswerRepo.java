package com.fantasticfour.shareyourrecipes.questionandanswer.repository;

import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {

    @Query(value="SELECT * FROM answers a WHERE a.deleted=" + false, nativeQuery = true)
    List<Answer> findAll();

    @Query(value= "SELECT * FROM answers a WHERE a.answerer_id=:answererId AND  a.deleted=" + false,nativeQuery = true)
    List<Answer> findByIdAnswerer(Long answererId);

    @Query(value= "SELECT * FROM  answers a WHERE a.deleted=FALSE AND id=:id", nativeQuery = true)
    Optional<Answer> findById(Long id); 


    
}
