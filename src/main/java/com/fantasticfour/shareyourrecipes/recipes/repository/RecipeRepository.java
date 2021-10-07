package com.fantasticfour.shareyourrecipes.recipes.repository;

import com.fantasticfour.shareyourrecipes.domains.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
}
