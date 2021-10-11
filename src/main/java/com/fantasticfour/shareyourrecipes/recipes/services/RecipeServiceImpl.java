package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public void createRecipe(Recipe recipe) {
        recipeRepository.save(recipe);

    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);

    }

    @Override
    public Recipe findById(Long idRecipe) {
        // TODO Auto-generated method stub

        return recipeRepository.findById(idRecipe).orElse(null);
    }

}
