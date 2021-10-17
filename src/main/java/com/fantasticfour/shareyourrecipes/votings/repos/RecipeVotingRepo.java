package com.fantasticfour.shareyourrecipes.votings.repos;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.RecipeVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeVotingRepo extends JpaRepository<RecipeVoting, VotingId> {
    List<RecipeVoting> findByRecipeId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into recipe_voting (recipe_id, voter_id, created_at) values (:recipeId, :voterId, now())", nativeQuery = true)
    void addVoting(Long recipeId, Long voterId);
}
