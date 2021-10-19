package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;

public interface RecipeService {

    List<RecipeDTO> findAll();

    void createRecipe(CreateRecipeDTO recipe);

    Recipe deleteRecipe(Long id);

    Recipe findById(Long id);

    RecipeDTO viewRecipeById(Long id);

}
