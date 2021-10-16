package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.user.UserRepo;

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
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public void createRecipe(CreateRecipeDTO recipe) {
        Recipe recipe2 = new Recipe();
        recipe2.setTitle(recipe.getTitle());
        recipe2.setImage(recipe.getImage());
        recipe2.setIngredients(recipe.getIngredients());
        recipe2.setSteps(recipe.getSteps());
        // recipe2.setCreator(userRepo.findEna); 
        recipe2.setGuideVideoUrl(recipe.getGuideVideoString());
        recipeRepository.save(recipe2);

    }

    @Override
    public void deleteRecipe(Long recipeid) {
        Recipe recipe = this.findById(recipeid);
        recipe.setDeleted(true);
        recipeRepository.save(recipe);

    }

    @Override
    public Recipe findById(Long idRecipe) {
        // TODO Auto-generated method stub

        return recipeRepository.findById(idRecipe).orElse(null);
    }

}
