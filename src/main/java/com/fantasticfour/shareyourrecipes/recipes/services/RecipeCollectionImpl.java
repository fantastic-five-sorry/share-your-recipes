package com.fantasticfour.shareyourrecipes.recipes.services;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeCollectionRepository;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeCollectionImpl implements RecipeCollectionService {

    private final RecipeCollectionRepository collectionRepository;
    private final UserRepo userRepo;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeCollectionImpl(RecipeCollectionRepository collectionRepository, UserRepo userRepo,
            RecipeRepository recipeRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepo = userRepo;
        this.recipeRepository = recipeRepository;
    }

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public Page<RecipeCollectionDTO> findAll(Pageable pageable) {
        // List<RecipeCollection> recipeCollections = collectionRepository.findAll();
        // List<RecipeCollectionDTO> recipeCollectionDTOs = new ArrayList<>();
        // recipeCollectionDTOs =
        // recipeCollections.stream().map(RecipeCollectionDTO::new).collect(Collectors.toList());
        return collectionRepository.findAll(pageable).map(RecipeCollectionDTO::new);
    }

    @Override
    public RecipeCollection findById(Long collectionId) {
        RecipeCollection recipeCollection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalStateException("collection not found"));
        return recipeCollection;
    }

    @Override
    public void createRecipeCollection(CreateRecipeCollectionDTO collection) throws Exception {

        RecipeCollection recipeCollection = new RecipeCollection();
        recipeCollection.setName(collection.getName());
        recipeCollection.setCreator(userRepo.findValidUserById(collection.getCreatorId()));
        // recipeCollection.setRecipes(recipeRepository.findById(collection.getRecipesId()));
        recipeCollection.setSlug(this.toSlug(collection.getName()));
        List<Recipe> recipes = new ArrayList<>();
        for (Long collectionDTOId : collection.getRecipesId()) {
            Recipe recipe = recipeRepository.findById(collectionDTOId).get();
            if (recipe == null) {
                throw new Exception("can`t found recipe");
            }
            recipes.add(recipe);
        }
        recipeCollection.setRecipes(recipes);
        collectionRepository.save(recipeCollection);

    }

    @Override
    public void deleteRecipeCollection(Long collectionId) throws Exception {
        RecipeCollection recipeCollection = this.findById(collectionId);
        // if (recipeCollection == null) {
        // throw new Exception("not found collection");
        // }
        recipeCollection.setDeleted(true);
        collectionRepository.save(recipeCollection);

    }

    @Override
    public RecipeCollectionDTO viewDRecipeCollectionDTO(Long collectionId) throws Exception {
        RecipeCollection collection = this.findById(collectionId);
        // if (collection == null) {
        // throw new Exception("not found collection");
        // }
        return new RecipeCollectionDTO(collection);
    }

    @Override
    public Page<RecipeCollectionDTO> findByCreatorId(Long creatorId, Pageable pageable) {
        // List<RecipeCollection> recipeCollections =
        // collectionRepository.findByCreatorId(creatorId);
        // List<RecipeCollectionDTO> collectionDTOs = new ArrayList<>();
        // collectionDTOs =
        // recipeCollections.stream().map(RecipeCollectionDTO::new).collect(Collectors.toList());
        return collectionRepository.findByCreatorId(creatorId, pageable).map(RecipeCollectionDTO::new);
    }

    @Override
    public void updateRecipeCollection(Long id, UpdateRecipeCollectionDTO collectionDTO) throws Exception {

        RecipeCollection recipeCollection = this.findById(id);
        // if (recipeCollection == null) {
        // throw new Exception("not found collection");
        // }

        for (Field field : collectionDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(collectionDTO) != null) {
                for (Field fieldCollection : recipeCollection.getClass().getDeclaredFields()) {
                    fieldCollection.setAccessible(true);
                    if (field.getName() == fieldCollection.getName()) {
                        fieldCollection.set(recipeCollection, field.get(collectionDTO));
                    }
                }
            }
        }

        collectionRepository.save(recipeCollection);

    }

}
