package com.fantasticfour.shareyourrecipes.recipes.controllers;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeCollectionService;
import com.nimbusds.oauth2.sdk.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class RecipeCollectionController {

    private final RecipeCollectionService recipeCollectionService;

    @Autowired
    public RecipeCollectionController(RecipeCollectionService recipeCollectionService) {
        this.recipeCollectionService = recipeCollectionService;
    }

    @GetMapping("")
    public ResponseEntity<?> recipeCollection(Pageable pageable) {
        // try {
        // } catch (Exception e) {
        //     // TODO: handle exception
        //     return ResponseEntity.badRequest().body("error : " + "Collection is empty");
        // }
        Page<RecipeCollectionDTO> collectionDTOs = recipeCollectionService.findAll(pageable);
        return new ResponseEntity<Page<RecipeCollectionDTO>>(collectionDTOs, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCollection(@RequestBody CreateRecipeCollectionDTO createRecipeCollectionDTO) {
        try {

            // System.out.println(createRecipeCollectionDTO);
            recipeCollectionService.createRecipeCollection(createRecipeCollectionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "create collection success");
    }

    // Update Collection dung List<Long> recipeIds chu nhi, @RequestBody kieu gi de
    // co dc List<Recipe>
    @PutMapping("/update")
    public ResponseEntity<?> updateCollection(@RequestBody UpdateRecipeCollectionDTO collectionDTO) {
        try {
            recipeCollectionService.updateRecipeCollection(collectionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message : " + "update collection success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable("id") Long id) {
        try {
            recipeCollectionService.deleteRecipeCollection(id);
            return ResponseEntity.ok().body("mesage : " + "delete collection success");

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> findCollectionBySlug(@PathVariable("slug") String slug) {
        // Long id = Long.parseLong(idRecipe);

        RecipeCollectionDTO collectionDTO;
        try {
            collectionDTO =  recipeCollectionService.getCollectionBySlug(slug);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return  new ResponseEntity< RecipeCollectionDTO>(collectionDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCollectionById(@PathVariable("id") Long id) {
        RecipeCollectionDTO collectionDTO;
        try {
            collectionDTO = recipeCollectionService.viewDRecipeCollectionDTO(id);
            return new ResponseEntity<RecipeCollectionDTO>(collectionDTO, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }

    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<?> findCollectionByCreatorId(@PathVariable("creatorId") Long creatorId, Pageable pageable) {

        try {
            Page<RecipeCollectionDTO> collectionDTOs = recipeCollectionService.findByCreatorId(creatorId, pageable);
            return new ResponseEntity<Page<RecipeCollectionDTO>>(collectionDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + "Collection is empty");
        }
    }

}