package com.fantasticfour.shareyourrecipes.votings.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.RecipeCollectionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCollectionVotingRepo extends JpaRepository<RecipeCollectionVoting, VotingId> {
    List<RecipeCollectionVoting> findByRecipeCollectionId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into recipe_collection_voting (recipe_collection_id, voter_id, type, created_at) values (:recipeCollectionId, :voterId, :type, now())", nativeQuery = true)
    void addVoting(Long recipeCollectionId, Long voterId, String type);
}