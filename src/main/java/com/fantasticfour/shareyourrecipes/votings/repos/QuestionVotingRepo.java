package com.fantasticfour.shareyourrecipes.votings.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.QuestionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionVotingRepo extends JpaRepository<QuestionVoting, VotingId> {
    List<QuestionVoting> findByQuestionId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into question_voting (question_id, voter_id, created_at) values (:questionId, :voterId, now())", nativeQuery = true)
    void addVoting(Long questionId, Long voterId);
}