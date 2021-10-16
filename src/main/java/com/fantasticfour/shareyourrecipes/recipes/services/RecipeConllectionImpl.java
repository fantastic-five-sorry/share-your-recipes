package com.fantasticfour.shareyourrecipes.recipes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeCollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeConllectionImpl implements RecipeCollectionService {

    private final RecipeCollectionRepository collectionRepository;
    @Autowired
    public RecipeConllectionImpl(RecipeCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    public List<RecipeCollection> findAll() {
        // TODO Auto-generated method stub
        return collectionRepository.findAll();
    }

    @Override
    public RecipeCollection findById(int idCollection) {
        // TODO Auto-generated method stub
        List<RecipeCollection> collections = collectionRepository.findAll();
        for (int  i = 0; i < collections.size(); i++) {
            if (Integer.parseInt(collections.get(i).getId().toString()) == idCollection) {
                return collections.get(i);
            }
        }
        return null;
    }

    @Override
    public void createRecipeCollection(RecipeCollection collection) {
        // TODO Auto-generated method stub
        collectionRepository.save(collection);
        
    }

    @Override
    public void deleteRecipeCollection(RecipeCollection collection) {
        // TODO Auto-generated method stub
        collectionRepository.delete(collection);
        
    }
    
}
