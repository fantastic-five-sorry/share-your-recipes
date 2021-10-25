package com.fantasticfour.shareyourrecipes.votings.repos;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.enums.VotingType;
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
    @Query(value = "insert into recipe_voting (recipe_id, voter_id, type, created_at) values (:recipeId, :voterId,:type, now())", nativeQuery = true)
    void addVoting(Long recipeId, Long voterId, String type);

    // @Modifying
    // @Transactional
    // @Query(value = "update recipe_voting set type=:type where recipe_id=:recipeId AND voter_id=:voterId", nativeQuery = true)
    // void updateVoting(Long recipeId, Long voterId, String type);
}
