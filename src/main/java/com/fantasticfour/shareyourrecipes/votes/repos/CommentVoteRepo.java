package com.fantasticfour.shareyourrecipes.votes.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votes.CommentVote;
import com.fantasticfour.shareyourrecipes.domains.votes.VoteId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepo extends JpaRepository<CommentVote, VoteId> {
    List<CommentVote> findByCommentId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into comment_votes (comment_id, voter_id, type, created_at) values (:commentId, :voterId, :type, now())", nativeQuery = true)
    void addVoting(Long commentId, Long voterId, String type);

    @Query(value = "SELECT v FROM comment_votes v WHERE v.recipe_id=:recipeId AND v.voter_id=:voterId", nativeQuery = true)
    List<CommentVote> getListCommentsHadVote(Long recipeId, Long voterId);
}