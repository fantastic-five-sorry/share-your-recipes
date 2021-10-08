package com.fantasticfour.shareyourrecipes.recipe;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {
    
    private final RecipeService recipeService;
    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public String getRecipe(Model model) {
        List<Recipe> recipes = recipeService.findAll();

        model.addAttribute("size", recipes.size());
        return "recipe";
    }
}
