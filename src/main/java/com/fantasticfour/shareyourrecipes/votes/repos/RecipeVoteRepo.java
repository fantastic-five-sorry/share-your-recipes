package com.fantasticfour.shareyourrecipes.votes.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votes.RecipeVote;
import com.fantasticfour.shareyourrecipes.domains.votes.VoteId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeVoteRepo extends JpaRepository<RecipeVote, VoteId> {
    List<RecipeVote> findByRecipeId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into recipe_votes (recipe_id, voter_id, type, created_at) values (:recipeId, :voterId,:type, now())", nativeQuery = true)
    void addVoting(Long recipeId, Long voterId, String type);

    // @Modifying
    // @Transactional
    // @Query(value = "update recipe_voting set type=:type where recipe_id=:recipeId AND voter_id=:voterId", nativeQuery = true)
    // void updateVoting(Long recipeId, Long voterId, String type);
}
