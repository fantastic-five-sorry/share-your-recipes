package com.fantasticfour.shareyourrecipes.questionandanswer.repository;

import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query(value="SELECT * FROM questions a WHERE a.deleted=" + false + " ORDER BY id DESC ", nativeQuery = true)
    Page<Question> findAll(Pageable pageable);

    @Query(value= "SELECT * FROM questions a WHERE a.creator_id=:creatorId AND a.deleted=" + false ,nativeQuery = true)
    List<Question> findByIdCreator(Long creatorId);

    @Query(value= "SELECT * FROM  questions q WHERE q.deleted=FALSE  AND id=:id", nativeQuery = true)
    Optional<Question> findById(Long id); 

    @Query(value = "SELECT * FROM  questions q WHERE q.deleted=FALSE AND q.status='APPROVED' AND id=:id", nativeQuery = true)
    Optional<Question> findQuestionApprovedById(Long id);

    @Query(value = "SELECT * FROM questions q WHERE q.slug=:slug", nativeQuery = true)
    Optional<Question> findBySlug(String slug);

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


    @Query(value = "SELECT * FROM questions q WHERE q.status=:status", nativeQuery = true)
    Page<Question> findByStatus(String status, Pageable pageable);
}
