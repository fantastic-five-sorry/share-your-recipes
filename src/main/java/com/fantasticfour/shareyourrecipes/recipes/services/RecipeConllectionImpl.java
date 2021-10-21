package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeCollectionDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeCollectionRepository;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.user.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

@Service
public class RecipeConllectionImpl implements RecipeCollectionService {

    private final RecipeCollectionRepository collectionRepository;
    private final UserRepo userRepo;
    private final RecipeRepository recipeRepository;
    @Autowired
    public RecipeConllectionImpl(RecipeCollectionRepository collectionRepository, UserRepo userRepo, RecipeRepository recipeRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepo = userRepo;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeCollection> findAll() {
        // TODO Auto-generated method stub
        List<RecipeCollection> recipeCollections = collectionRepository.findAll();
        List<RecipeCollectionDTO> recipeCollectionDTOs = new ArrayList<>();
        for (int  i = 0; i <  recipeCollections.size(); i++) {
            RecipeCollectionDTO dto = new RecipeCollectionDTO(recipeCollections.get(i));
            
            // dto.setName(recipeCollections.get(i).getName());
            // dto.setRecipes(recipeCollections.get(i).getRecipes());
            // dto.setVoteCount(recipeCollections.get(i).getVoteCount());
            // dto.setCreator(recipeCollections.get(i).getCreator());
            recipeCollectionDTOs.add(dto);
        }
        return  recipeCollections;
    }

    @Override
    public RecipeCollection findById(Long collectionId) {
        // TODO Auto-generated method stub
        RecipeCollection recipeCollection = collectionRepository.findById(collectionId).orElse(null);
        return recipeCollection;
    }

    

    @Override
    public void createRecipeCollection(CreateRecipeCollectionDTO collection) throws Exception {

        // TODO Auto-generated method stub
        RecipeCollection recipeCollection = new RecipeCollection();
        recipeCollection.setName(collection.getName());
        recipeCollection.setCreator(userRepo.findEnabledUserById(collection.getCreatorId()));
        // recipeCollection.setRecipes(recipeRepository.findById(collection.getRecipesId()));
        List<Recipe> recipes = new ArrayList<>();
        for  (Long collectionDTOId : collection.getRecipesId()) {
            Recipe recipe = recipeRepository.findById(collectionDTOId).get();
            if (recipe == null) {
                throw new Exception("can`t found recipe");
            }
            recipes.add(recipe);
        }
        recipeCollection.setRecipes(recipes);
        collectionRepository.save(recipeCollection);
        
    }

    @Override
    public void deleteRecipeCollection(Long collectionId) {
        // TODO Auto-generated method stub
        RecipeCollection recipeCollection  = this.findById(collectionId);
        recipeCollection.setDeleted(true);
        collectionRepository.delete(recipeCollection);
        
    }

    @Override
    public RecipeCollectionDTO viewDRecipeCollectionDTO(Long collectionId) {
        // TODO Auto-generated method stub
        RecipeCollection collection = this.findById(collectionId);
        RecipeCollectionDTO collectionDTO = new RecipeCollectionDTO();
        if (collection != null ) {
            // collectionDTO.setCreator((collection.getCreator()));
            // collectionDTO.setName(collection.getName());
            // collectionDTO.setRecipes(collection.getRecipes());
            // collectionDTO.setVoteCount(collection.getVoteCount());
            collectionDTO = new RecipeCollectionDTO(collection);
        }
        return collectionDTO;
    }
    
}
