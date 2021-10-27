package com.fantasticfour.shareyourrecipes.recipes.services;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepo userRepo;
    @Value("${lvl.app.maxSlugRandomStringLength}")
    private int SHORT_ID_LENGTH;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepo userRepo) {
        this.recipeRepository = recipeRepository;
        this.userRepo = userRepo;
    }

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String shortId = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        input = input + " " + shortId;
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return normalized.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public Page<RecipeDTO> findAll(Pageable pageable) {
        // Page<Recipe> recipes = recipeRepository.findAll();
        // Page<RecipeDTO> recipeDTOs = new ArrayList<>();
        // for (Recipe recipe: recipes) {
        //     recipeDTOs.add(new RecipeDTO(recipe));
        // }
        return recipeRepository.findAll(pageable).map(RecipeDTO::new);

    public List<RecipeDTO> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOs = new ArrayList<>();
        for (Recipe recipe : recipes) {
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
    public void deleteRecipe(Long recipeid) throws Exception {
        Recipe recipe = this.findById(recipeid);
        // if (recipe == null) {
        // throw new Exception("can't find recipe");
        // }
        recipe.setDeleted(true);
        recipeRepository.save(recipe);

    }

    @Override
    public Recipe findById(Long idRecipe) {
        // TODO Auto-generated method stub
        // xai orElseThrow(() -> new IllegalStateException("Recipe not found"))
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new IllegalStateException("recipe not found"));

        return recipe;
    }

    @Override
    public RecipeDTO viewRecipeById(Long id) throws Exception {
        // TODO Auto-generated method stub
        Recipe recipe = this.findById(id);

        return new RecipeDTO(recipe);
    }

    @Override
    public void updateRecipe(Long id, UpdateRecipeDTO updateRecipeDTO ) throws Exception {
        // TODO Auto-generated method stub
        Recipe recipe = this.findById(id);
        // if (recipe == null) {
        //     throw new Exception("not found recipe");
            
        // }

        for (Field field : updateRecipeDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(updateRecipeDTO) != null) {
                for (Field fieldRecipe : recipe.getClass().getDeclaredFields()) {
                    fieldRecipe.setAccessible(true);
                    
                    if (field.getName() == fieldRecipe.getName()) {
                        // System.out.println(field.get(updateRecipeDTO));
                        // System.out.println(field.get(updateRecipeDTO));
                        fieldRecipe.set(recipe, field.get(updateRecipeDTO));
                    }
                }
            }

        }
        // System.out.println(recipe.getPrice());
        recipeRepository.save(recipe);
    }

    @Override
    public Page<RecipeDTO> findByStatus(String recipeStatus, Pageable pageable) {
        // TODO Auto-generated method stub
        return recipeRepository.findByStatus(recipeStatus, pageable).map(RecipeDTO::new);
    }

}
