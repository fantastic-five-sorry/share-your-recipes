package com.fantasticfour.shareyourrecipes.recipes.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("")
    public ResponseEntity<?> recipe(Pageable pageable) {

        // try {

        // } catch (Exception e) {
        // //TODO: handle exception
        // return ResponseEntity.badRequest().body("error: " + "Recipes is empty");
        // }
        Page<RecipeDTO> recipes = recipeService.findAll(pageable);
        return new ResponseEntity<Page<RecipeDTO>>(recipes, HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> findRecipeBySlug(@PathVariable("slug") String slug) {
        // Long id = Long.parseLong(idRecipe);

        RecipeDTO recipeDTO;
        try {
            recipeDTO = recipeService.getRecipeBySlug(slug);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeDTO recipe) {
        try {
            System.out.println(recipe);
            recipeService.createRecipe(recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add recipe success");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRecipe(@RequestBody UpdateRecipeDTO updateRecipeDTO) {
        try {
            recipeService.updateRecipe(updateRecipeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "update recipe success");
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
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable("status") RecipeStatus status, Pageable pageable) {
        try {
            // System.out.println(status.toString());
            return new ResponseEntity<Page<RecipeDTO>>(recipeService.findByStatus(status.toString(), pageable),
                    HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

}
