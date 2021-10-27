package com.fantasticfour.shareyourrecipes.votings.services;

import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.CommentVotingDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void writeCommentToRecipe(NewCommentDto comment);

    CommentDto getComment(Long id);

    Page<CommentVotingDto> getCommentVotingsOfRecipe(Long recipeId, Long voterId, Pageable page);

    Page<CommentDto> getCommentsOfRecipe(Long recipeId, Pageable page);

    void editRecipeComment(EditCommentDto dto);

    void deleteCommentToRecipe(Long id);

    Page<CommentDto> getAllComments(Pageable page);
}
