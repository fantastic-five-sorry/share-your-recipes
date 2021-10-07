package com.fantasticfour.shareyourrecipes.recipes.repository;

import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipeId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedRecipeRepository extends JpaRepository<PurchasedRecipe, PurchasedRecipeId> {
    
}
