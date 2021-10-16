package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.PurchasedRecipe;

public interface PurchasedRecipeService {

    List<PurchasedRecipe> findAll();

    void save(PurchasedRecipe recipe);

}
