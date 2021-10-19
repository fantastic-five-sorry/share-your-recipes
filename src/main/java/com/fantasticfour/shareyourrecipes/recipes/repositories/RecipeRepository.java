package com.fantasticfour.shareyourrecipes.recipes.repositories;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    

    @Query(value = "SELECT * FROM recipes r WHERE r.creator_id=:creatorId", nativeQuery = true)
    List<Recipe> findByCreatorId(Long creatorId);

    @Query(value = "SELECT * FROM recipes r WHERE r.deleted=" + false, nativeQuery = true)
    List<Recipe> findAll();

}
