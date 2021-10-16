package com.fantasticfour.shareyourrecipes.recipes.repositories;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long>{
    
}
