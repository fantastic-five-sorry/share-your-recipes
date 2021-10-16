package com.fantasticfour.shareyourrecipes.votings.repos;

import com.fantasticfour.shareyourrecipes.domains.votings.AnswerVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerVotingRepo extends JpaRepository<AnswerVoting, VotingId> {
    
}