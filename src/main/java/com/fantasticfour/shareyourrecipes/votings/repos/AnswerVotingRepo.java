package com.fantasticfour.shareyourrecipes.votings.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.AnswerVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVotingRepo extends JpaRepository<AnswerVoting, VotingId> {

    List<AnswerVoting> findByAnswerId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into answer_voting (answer_id, voter_id, created_at) values (:answerId, :voterId, now())", nativeQuery = true)
    void addVoting(Long answerId, Long voterId);
}