package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
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
        // List<Recipe> recipes = recipeRepository.findAll();
        // for (int i = 0; i < recipes.size(); i++) {
        // if (Integer.parseInt(recipes.get(i).getId().toString()) == idRecipe) {
        // return recipes.get(i);
        // }
        // }
        // return null;

        return recipeRepository.findById(idRecipe).orElse(null);
    }

}
