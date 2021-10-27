package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeCollectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeCollectionService {

    Page<RecipeCollectionDTO> findAll(Pageable pageable);

    void createRecipeCollection(CreateRecipeCollectionDTO collection) throws Exception;

    void deleteRecipeCollection(Long collectionId) throws Exception;

    void updateRecipeCollection(Long id, UpdateRecipeCollectionDTO collectionDTO) throws Exception;

    RecipeCollection findById(Long collectionId);

    RecipeCollectionDTO viewDRecipeCollectionDTO(Long collectionId) throws Exception;

    Page<RecipeCollectionDTO> findByCreatorId(Long creatorId, Pageable pageable);


}
