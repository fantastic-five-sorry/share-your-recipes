package com.fantasticfour.shareyourrecipes.votes.repos;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM Comments WHERE recipe_id=:id ORDER BY id DESC", nativeQuery = true)
    Page<Comment> findByRecipeId(Long id, Pageable page);

    @Modifying
    @Transactional
    @Query("update Comment u set u.upVoteCount = u.upVoteCount - 1 where u.id = :id")
    void decreaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Comment u set u.upVoteCount = u.upVoteCount + 1 where u.id = :id")
    void increaseUpVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Comment u set u.downVoteCount = u.downVoteCount - 1 where u.id = :id")
    void decreaseDownVoteCount(Long id);

    @Modifying
    @Transactional
    @Query("update Comment u set u.downVoteCount = u.downVoteCount + 1 where u.id = :id")
    void increaseDownVoteCount(Long id);
}
