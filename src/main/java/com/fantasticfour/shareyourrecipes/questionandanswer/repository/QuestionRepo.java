package com.fantasticfour.shareyourrecipes.questionandanswer.repository;

import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query(value="SELECT * FROM questions a WHERE a.deleted=" + false, nativeQuery = true)
    List<Question> findAll();

    @Query(value= "SELECT * FROM questions a WHERE a.creator_id=:creatorId AND a.deleted=" + false ,nativeQuery = true)
    List<Question> findByIdCreator(Long creatorId);

    @Query(value= "SELECT * FROM  questions q WHERE q.deleted=FALSE AND id=:id", nativeQuery = true)
    Optional<Question> findById(Long id); 

    @Modifying
    @Transactional
    @Query("update Question u set u.upVoteCount = u.upVoteCount - 1 where u.id = :id")
    void decreaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Question u set u.upVoteCount = u.upVoteCount + 1 where u.id = :id")
    void increaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Question u set u.downVoteCount = u.downVoteCount - 1 where u.id = :id")
    void decreaseDownVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Question u set u.downVoteCount = u.downVoteCount + 1 where u.id = :id")
    void increaseDownVoteCount(Long id);
}
