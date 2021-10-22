package com.fantasticfour.shareyourrecipes.recipes.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

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

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private  final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOs = new ArrayList<>();
        for (Recipe recipe: recipes) {
            recipeDTOs.add(new RecipeDTO(recipe));
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
        recipe2.setSlug(this.toSlug(recipe.getTitle()));
        recipeRepository.save(recipe2);
        // System.out.println("okeoke");

    }

    @Override
    public Recipe deleteRecipe(Long recipeid)  throws Exception {
        Recipe recipe = this.findById(recipeid);
        if (recipe == null) {
            throw new Exception("can't find recipe");
        }
        recipe.setDeleted(true);
        recipeRepository.save(recipe);
        return recipe;

    }

    @Override
    public Recipe findById(Long idRecipe) {
        // TODO Auto-generated method stub

        Recipe recipe =recipeRepository.findById(idRecipe).orElse(null);
    

        return recipe;
    }

    @Override
    public RecipeDTO viewRecipeById(Long id) throws Exception {
        // TODO Auto-generated method stub
        Recipe recipe = this.findById(id);
        
        if (recipe == null) {
            throw new Exception("can't find recipe");
        }
        return new RecipeDTO(recipe);
    }

}
