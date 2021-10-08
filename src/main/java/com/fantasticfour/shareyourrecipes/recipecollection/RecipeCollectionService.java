package com.fantasticfour.shareyourrecipes.recipecollection;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.RecipeCollection;

public interface RecipeCollectionService {

    List<RecipeCollection> findAll();

    void createRecipeCollection(RecipeCollection collection);

    void deleteRecipeCollection(RecipeCollection collection);

    RecipeCollection findById(int idCollection);


    
}
