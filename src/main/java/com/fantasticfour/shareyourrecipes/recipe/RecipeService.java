package com.fantasticfour.shareyourrecipes.recipe;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;

public interface RecipeService {

    List<Recipe> findAll();
    void createRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
    Recipe findById(int idRecipe);
    
}
