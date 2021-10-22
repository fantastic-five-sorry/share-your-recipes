package com.fantasticfour.shareyourrecipes.recipes.repositories;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long>{

    @Query(value = "SELECT * FROM recipes_collection r WHERE r.deleted=" + false, nativeQuery = true)
    List<RecipeCollection> findAll();

    @Query(value ="SELECT * FROM recipes_collection r WHERE r.deleted=false AND r.id=:id", nativeQuery = true)
    Optional<RecipeCollection> findById(Long id);

    @Query(value ="SELECT * FROM recipes_collection r  WHERE r.deleted=false AND r.creator_id=:creatorId",  nativeQuery = true)
    List<RecipeCollection> findByCreatorId(Long creatorId);
    
    
}
