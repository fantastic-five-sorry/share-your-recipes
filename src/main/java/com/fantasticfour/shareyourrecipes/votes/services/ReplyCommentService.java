package com.fantasticfour.shareyourrecipes.votes.services;

import com.fantasticfour.shareyourrecipes.votes.dtos.NewReplyCommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.ReplyCommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyCommentService {
    Page<ReplyCommentDto> findReplyComment(Long idParent, Pageable pageable);
    ReplyCommentDto addReplyComment(NewReplyCommentDto newReplyCommentDto);
    void deleteReplyComment(Long id);
}
