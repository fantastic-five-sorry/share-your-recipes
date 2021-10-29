package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

    Page<RecipeDTO> findAll(Pageable pageable);

    Page<RecipeDTO> findByStatus(String recipeStatus, Pageable pageable);

    void createRecipe(CreateRecipeDTO recipe);

    void deleteRecipe(Long id)  throws Exception;

    void updateRecipe(UpdateRecipeDTO updateRecipeDTO) throws Exception;

    Recipe findById(Long id);

    RecipeDTO getRecipeBySlug(String slug) throws Exception;

    RecipeDTO viewRecipeById(Long id) throws Exception;

}
