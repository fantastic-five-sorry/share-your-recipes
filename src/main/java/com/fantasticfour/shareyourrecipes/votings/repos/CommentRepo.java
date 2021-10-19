package com.fantasticfour.shareyourrecipes.votings.repos;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query(value="SELECT * FROM Comments WHERE recipe_id=:id ORDER BY id ASC", nativeQuery = true)
    List<Comment> findByRecipeId(Long id);
}
