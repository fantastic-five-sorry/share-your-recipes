package com.fantasticfour.shareyourrecipes.recipes;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/test/recipes")
    public List<Recipe> recipe() {
        List<Recipe> recipes = recipeService.findAll();

        System.out.println(recipes);
        // model.addAttribute("recipes", recipes);

        return recipes;
    }

}
