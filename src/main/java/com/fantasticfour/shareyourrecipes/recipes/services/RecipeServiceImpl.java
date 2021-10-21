package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepo userRepo;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepo userRepo) {
        this.recipeRepository = recipeRepository;
        this.userRepo = userRepo;
    }

    @Override
    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOs = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setTitle(recipes.get(i).getTitle());
            recipeDTO.setImage(recipes.get(i).getImage());
            recipeDTO.setIngredients(recipes.get(i).getIngredients());
            recipeDTO.setSteps(recipes.get(i).getSteps());
            recipeDTO.setGuideVideoString(recipes.get(i).getGuideVideoUrl());
            recipeDTO.setCreator(recipes.get(i).getCreator());

            recipeDTO.setStatus(recipes.get(i).getStatus().toString());
            recipeDTO.setPrice(recipes.get(i).getPrice());
            recipeDTOs.add(recipeDTO);
        }
        return recipeDTOs;
    }

    @Override
    public void createRecipe(CreateRecipeDTO recipe) {
        Recipe recipe2 = new Recipe();
        recipe2.setTitle(recipe.getTitle());
        recipe2.setImage(recipe.getImage());
        recipe2.setIngredients(recipe.getIngredients());
        recipe2.setSteps(recipe.getSteps());
        recipe2.setCreator(userRepo.findValidUserById(recipe.getCreatorId()));
        recipe2.setGuideVideoUrl(recipe.getGuideVideoString());
        recipeRepository.save(recipe2);
        // System.out.println("okeoke");

    }

    @Override
    public Recipe deleteRecipe(Long recipeid) {
        Recipe recipe = this.findById(recipeid);
        if (recipe != null){
            recipe.setDeleted(true);
            recipeRepository.save(recipe);
        }
        return recipe;

    }

    @Override
    public Recipe findById(Long idRecipe) {
        // TODO Auto-generated method stub

        Recipe recipe =recipeRepository.findById(idRecipe).orElse(null);
    

        return recipe;
    }

    @Override
    public RecipeDTO viewRecipeById(Long id) {
        // TODO Auto-generated method stub
        Recipe recipe = this.findById(id);
        RecipeDTO recipeDTO = new RecipeDTO();
        if (recipe != null) {
            recipeDTO.setTitle(recipe.getTitle());
            recipeDTO.setImage(recipe.getImage());
            recipeDTO.setIngredients(recipe.getIngredients());
            recipeDTO.setSteps(recipe.getSteps());
            recipeDTO.setGuideVideoString(recipe.getGuideVideoUrl());
            recipeDTO.setCreator(recipe.getCreator());

            recipeDTO.setStatus(recipe.getStatus().toString());

            recipeDTO.setPrice(recipe.getPrice());
        }
        return recipeDTO;
    }

}
