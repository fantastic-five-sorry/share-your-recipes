package com.fantasticfour.shareyourrecipes.votes.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votes.RecipeCollectionVote;
import com.fantasticfour.shareyourrecipes.domains.votes.VoteId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCollectionVoteRepo extends JpaRepository<RecipeCollectionVote, VoteId> {
    List<RecipeCollectionVote> findByRecipeCollectionId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into recipe_collection_votes (recipe_collection_id, voter_id, type, created_at) values (:recipeCollectionId, :voterId, :type, now())", nativeQuery = true)
    void addVoting(Long recipeCollectionId, Long voterId, String type);
}