package com.fantasticfour.shareyourrecipes.votings.repos;

import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.CommentVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVotingRepo extends JpaRepository<CommentVoting, VotingId> {
    List<CommentVoting> findByCommentId(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into comment_voting (comment_id, voter_id, created_at) values (:commentId, :voterId, now())", nativeQuery = true)
    void addVoting(Long commentId, Long voterId);
}