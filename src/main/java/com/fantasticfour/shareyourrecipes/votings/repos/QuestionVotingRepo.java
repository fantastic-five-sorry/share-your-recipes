package com.fantasticfour.shareyourrecipes.votings.repos;

import com.fantasticfour.shareyourrecipes.domains.votings.QuestionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionVotingRepo extends JpaRepository<QuestionVoting, VotingId> {
    
}