package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.recipes.repositories.PurchasedRecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasedRecipeServiceImpl implements PurchasedRecipeService {

    private final PurchasedRecipeRepository purchasedRecipeRepository;

    @Autowired
    public PurchasedRecipeServiceImpl(PurchasedRecipeRepository purchasedRecipeRepository) {
        this.purchasedRecipeRepository = purchasedRecipeRepository;
    }

    @Override
    public List<PurchasedRecipe> findAll() {
        return purchasedRecipeRepository.findAll();
    }

    @Override
    public void save(PurchasedRecipe recipe) {

        purchasedRecipeRepository.save(recipe);
    }

}
