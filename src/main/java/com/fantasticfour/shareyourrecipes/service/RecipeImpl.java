package com.fantasticfour.shareyourrecipes.service;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;
import com.fantasticfour.shareyourrecipes.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        // TODO Auto-generated method stub
        return recipeRepository.findAll();
    }

    @Override
    public void createRecipe(Recipe recipe) {
        // TODO Auto-generated method stub
        recipeRepository.save(recipe);
        
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        // TODO Auto-generated method stub
        recipeRepository.delete(recipe);
        
    }

    @Override
    public Recipe findById(int idRecipe) {
        // TODO Auto-generated method stub
        List<Recipe> recipes = recipeRepository.findAll();
        for (int i = 0; i < recipes.size(); i++) {
            if (Integer.parseInt(recipes.get(i).getId().toString()) == idRecipe) {
                return recipes.get(i);
            }
        }
        return null;
    }

    
}
