package com.fantasticfour.shareyourrecipes.recipes.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT * FROM recipes r WHERE r.creator_id=:creatorId", nativeQuery = true)
    List<Recipe> findByCreatorId(Long creatorId);

    @Query(value = "SELECT * FROM recipes r WHERE r.deleted=" + false, nativeQuery = true)
    List<Recipe> findAll();

    @Modifying
    @Transactional
    @Query("update Recipe u set u.voteCount = u.voteCount - 1 where u.id = :id")
    void decreaseVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Recipe u set u.voteCount = u.voteCount + 1 where u.id = :id")
    void increaseVoteCount(Long id);

}
