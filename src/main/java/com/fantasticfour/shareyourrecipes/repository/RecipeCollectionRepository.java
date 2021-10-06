package com.fantasticfour.shareyourrecipes.repository;

import com.fantasticfour.shareyourrecipes.domains.RecipeCollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long>{
    
}
