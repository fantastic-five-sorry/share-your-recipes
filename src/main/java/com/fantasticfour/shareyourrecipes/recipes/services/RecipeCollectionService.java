package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

public interface RecipeCollectionService {

    List<RecipeCollection> findAll();

    void createRecipeCollection(RecipeCollection collection);

    void deleteRecipeCollection(RecipeCollection collection);

    RecipeCollection findById(int idCollection);


    
}
