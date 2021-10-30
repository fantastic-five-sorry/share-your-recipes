package com.fantasticfour.shareyourrecipes.recipes.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT * FROM recipes r WHERE r.creator_id=:creatorId", nativeQuery = true)
    List<Recipe> findByCreatorId(Long creatorId);

    @Query(value = "SELECT * FROM recipes r WHERE r.deleted=" + false, nativeQuery = true)
    Page<Recipe> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM recipes r WHERE r.status=:status", nativeQuery = true)
    Page<Recipe> findByStatus(String status, Pageable pageable);

    @Query("SELECT r FROM Recipe r WHERE r.deleted=FALSE AND id=:id")
    Optional<Recipe> findById(Long id);

    @Query(value = "SELECT * FROM recipes r WHERE r.slug=:slug AND r.status='APPROVED'", nativeQuery = true)
    Optional<Recipe> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query("update Recipe u set u.upVoteCount = u.upVoteCount - 1 where u.id = :id")
    void decreaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Recipe u set u.upVoteCount = u.upVoteCount + 1 where u.id = :id")
    void increaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Recipe u set u.downVoteCount = u.downVoteCount - 1 where u.id = :id")
    void decreaseDownVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Recipe u set u.downVoteCount = u.downVoteCount + 1 where u.id = :id")
    void increaseDownVoteCount(Long id);

}
