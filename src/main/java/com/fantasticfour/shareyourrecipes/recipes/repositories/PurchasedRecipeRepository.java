package com.fantasticfour.shareyourrecipes.recipes.repositories;

import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipeId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchasedRecipeRepository extends JpaRepository<PurchasedRecipe, PurchasedRecipeId> {

    @Query(value = "SELECT * FROM purchased_recipes r WHERE r.recipe_id=:recipeId", nativeQuery = true)
    List<PurchasedRecipe> findByRecipeId(Long recipeId);

    @Query(value = "SELECT * FROM purchased_recipes r WHERE r.creator_id=:creatorId", nativeQuery = true)
    List<PurchasedRecipe> findByCreatorId(Long creatorId);

    @Query(value = "SELECT r FROM PurchasedRecipe r JOIN FETCH r.creator c WHERE c.email=:email")
    List<PurchasedRecipe> findByCreatorEmail(String email);
}
