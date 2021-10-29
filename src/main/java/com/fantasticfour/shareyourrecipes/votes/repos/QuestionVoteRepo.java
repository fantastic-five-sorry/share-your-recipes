package com.fantasticfour.shareyourrecipes.votes.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votes.QuestionVote;
import com.fantasticfour.shareyourrecipes.domains.votes.VoteId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionVoteRepo extends JpaRepository<QuestionVote, VoteId> {
    List<QuestionVote> findByQuestionId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into question_votes (question_id, voter_id, type, created_at) values (:questionId, :voterId, :type, now())", nativeQuery = true)
    void addVoting(Long questionId, Long voterId, String type);
}