package com.fantasticfour.shareyourrecipes.votes.repos;

import com.fantasticfour.shareyourrecipes.domains.ReplyComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyCommentRepo extends JpaRepository<ReplyComment, Long>  {

    @Query(value = "SELECT * FROM reply_comments WHERE parent_id=:idParent ORDER BY id DESC", nativeQuery = true)
    Page<ReplyComment> findReplyComment(Long idParent, Pageable page);
}
