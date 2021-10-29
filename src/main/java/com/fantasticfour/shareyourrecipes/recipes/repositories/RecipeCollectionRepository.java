package com.fantasticfour.shareyourrecipes.recipes.repositories;

import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long>{

    @Query(value = "SELECT * FROM recipes_collection r WHERE r.deleted=" + false, nativeQuery = true)
    Page<RecipeCollection> findAll(Pageable pageable);

    @Query(value ="SELECT * FROM recipes_collection r WHERE r.deleted=false AND r.id=:id", nativeQuery = true)
    Optional<RecipeCollection> findById(Long id);

    @Query(value ="SELECT * FROM recipes_collection r  WHERE r.deleted=false AND r.creator_id=:creatorId",  nativeQuery = true)
    Page<RecipeCollection> findByCreatorId(Long creatorId, Pageable pageable);
    
    @Query(value = "SELECT * FROM recipes_collection  r WHERE r.slug=:slug", nativeQuery = true)
    Optional<RecipeCollection> findBySlug(String slug);
    
    @Modifying
    @Transactional
    @Query("update RecipeCollection u set u.upVoteCount = u.upVoteCount - 1 where u.id = :id")
    void decreaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update RecipeCollection u set u.upVoteCount = u.upVoteCount + 1 where u.id = :id")
    void increaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update RecipeCollection u set u.downVoteCount = u.downVoteCount - 1 where u.id = :id")
    void decreaseDownVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update RecipeCollection u set u.downVoteCount = u.downVoteCount + 1 where u.id = :id")
    void increaseDownVoteCount(Long id);

}
