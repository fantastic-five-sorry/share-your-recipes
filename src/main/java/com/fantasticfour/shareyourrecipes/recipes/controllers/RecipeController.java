package com.fantasticfour.shareyourrecipes.recipes.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @GetMapping("/")

    public ResponseEntity<?> recipe() {


        List<RecipeDTO> recipes = recipeService.findAll();
        if (recipes.size() > 0) {
            return new ResponseEntity<List<RecipeDTO>>(recipes, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("error: " + "Recipes is empty");
 
    }

    @PostMapping("/")
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeDTO recipe) {
        try {
            recipeService.createRecipe(recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add recipe success");
    }

    // Phan nay deletemapping
    @DeleteMapping("/{idRecipe}")

    public ResponseEntity<?> deleteRecipe(@PathVariable("idRecipe") Long idRecipe) {
        try {
            recipeService.deleteRecipe(idRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "delete recipe success");

    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<?> findRecipeById(@PathVariable("idRecipe") Long idRecipe) {
        // Long id = Long.parseLong(idRecipe);

        RecipeDTO recipeDTO;
        try {
            recipeDTO = recipeService.viewRecipeById(idRecipe);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return  new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

}
