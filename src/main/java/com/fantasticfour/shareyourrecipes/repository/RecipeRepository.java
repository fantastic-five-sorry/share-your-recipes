package com.fantasticfour.shareyourrecipes.repository;

import com.fantasticfour.shareyourrecipes.domains.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
}
