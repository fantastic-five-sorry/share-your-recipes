package com.fantasticfour.shareyourrecipes.votes.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votes.AnswerVote;
import com.fantasticfour.shareyourrecipes.domains.votes.VoteId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVoteRepo extends JpaRepository<AnswerVote, VoteId> {

    List<AnswerVote> findByAnswerId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into answer_votes (answer_id, voter_id, type, created_at) values (:answerId, :voterId, :type, now())", nativeQuery = true)
    void addVoting(Long answerId, Long voterId, String type);
}