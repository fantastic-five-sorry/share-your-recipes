package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;

public interface RecipeCollectionService {

    List<RecipeCollection> findAll();

    void createRecipeCollection(CreateRecipeCollectionDTO collection) throws Exception;

    void deleteRecipeCollection(Long collectionId);

    RecipeCollection findById(Long collectionId);

    RecipeCollectionDTO viewDRecipeCollectionDTO(Long collectionId);

    
}
