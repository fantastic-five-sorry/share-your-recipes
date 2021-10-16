package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

public interface RecipeService {

    List<Recipe> findAll();
    void createRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
    Recipe findById(Long id);
    
}
