package com.fantasticfour.shareyourrecipes.votes.services;

import com.fantasticfour.shareyourrecipes.votes.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.CommentVoteDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.NewCommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDto writeCommentToRecipe(NewCommentDto comment);

    CommentDto getComment(Long id);

    Page<CommentVoteDto> getCommentVotingsOfRecipe(Long recipeId, Long voterId, Pageable page);

    Page<CommentDto> getCommentsOfRecipe(Long recipeId, Pageable page);

    void editRecipeComment(EditCommentDto dto);

    void deleteCommentToRecipe(Long id);

    Page<CommentDto> getAllComments(Pageable page);
}
