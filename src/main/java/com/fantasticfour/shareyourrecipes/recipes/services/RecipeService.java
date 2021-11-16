package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.enums.VoteType;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

    Page<RecipeDTO> findAll(Pageable pageable);

    Page<RecipeDTO> findAllApprovedRecipes(Pageable pageable);

    Page<RecipeDTO> findByStatus(String recipeStatus, Pageable pageable);

    Page<RecipeDTO> findAllSortByUpVoteCount(Pageable pageable);

    void createRecipe(CreateRecipeDTO recipe);

    void deleteRecipe(Long id) throws Exception;

    void updateRecipe(UpdateRecipeDTO updateRecipeDTO) throws Exception;

    Recipe findById(Long id);

    RecipeDTO getRecipeBySlug(String slug) throws Exception;

    VoteType getVotedStatus(Long recipeId, Long uid);

    RecipeDTO viewRecipeById(Long id) throws Exception;

    Page<RecipeDTO> findByCreator(Long uid, Pageable page);

    Page<RecipeDTO> findAllNotApprovedRecipesByCreator(Long id, Pageable pageable);

    Page<RecipeDTO> findAllApprovedRecipesByCreator(Long id, Pageable pageable);
}
