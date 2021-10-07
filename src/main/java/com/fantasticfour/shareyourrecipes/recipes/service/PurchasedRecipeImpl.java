package com.fantasticfour.shareyourrecipes.recipes.service;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.recipes.repository.PurchasedRecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasedRecipeImpl implements PurchasedRecipeService{

    private final PurchasedRecipeRepository purchasedRecipeRepository;
    @Autowired
    public PurchasedRecipeImpl(PurchasedRecipeRepository purchasedRecipeRepository) {
        this.purchasedRecipeRepository = purchasedRecipeRepository;
    }


    @Override
    public List<PurchasedRecipe> findAll() {
        // TODO Auto-generated method stub
        return purchasedRecipeRepository.findAll();
    }




    
}
