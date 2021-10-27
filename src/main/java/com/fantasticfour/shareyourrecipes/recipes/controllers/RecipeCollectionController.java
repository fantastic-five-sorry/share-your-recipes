package com.fantasticfour.shareyourrecipes.recipes.controllers;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeCollectionService;
import com.nimbusds.oauth2.sdk.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/")
    public ResponseEntity<?> recipeCollection() {
        List<RecipeCollectionDTO> collectionDTOs = recipeCollectionService.findAll();
        if (collectionDTOs.size() > 0) {
            return new ResponseEntity<List<RecipeCollectionDTO>>(collectionDTOs, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("error : " + "Collection is empty");
    }

    @PostMapping("/createCollection")
    public ResponseEntity<?> createCollection(@RequestBody CreateRecipeCollectionDTO createRecipeCollectionDTO) {
        try {

            System.out.println(createRecipeCollectionDTO);
            recipeCollectionService.createRecipeCollection(createRecipeCollectionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "create collection success");
    }

    @DeleteMapping("/{idcollection}")
    public ResponseEntity<?> deleteCollection(@PathVariable("idcollection") Long idcollection) {
        try {
            recipeCollectionService.deleteRecipeCollection(idcollection);
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.badRequest().body("error : " + e.getMessage());
        }
        return ResponseEntity.ok().body("mesage : " + "delete collection success");
    }

    @GetMapping("/{idcollection}")
    public ResponseEntity<?> findCollectionById(@PathVariable("idcollection") Long idcollection) {
        RecipeCollectionDTO collectionDTO;
        try {
            collectionDTO = recipeCollectionService.viewDRecipeCollectionDTO(idcollection);
        } catch (Exception e) {
            //TODO: handle exception
            return  ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return  new ResponseEntity<RecipeCollectionDTO>(collectionDTO, HttpStatus.OK);
    
    }

    @GetMapping("/creator/{creatorId}")
    public  ResponseEntity<?> findCollectionByCreatorId(@PathVariable("creatorId") Long creatorId) {
        List<RecipeCollectionDTO> collectionDTOs = recipeCollectionService.findByCreatorId(creatorId);
        if (collectionDTOs.size() > 0) {
            return new ResponseEntity<List<RecipeCollectionDTO>>(collectionDTOs, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("error : " + "Collection is empty");
    }


}