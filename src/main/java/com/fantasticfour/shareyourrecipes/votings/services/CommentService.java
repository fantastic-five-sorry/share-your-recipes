package com.fantasticfour.shareyourrecipes.votings.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void writeCommentToRecipe(NewCommentDto comment);

    CommentDto getComment(Long id);

    Page<CommentDto> getCommentsOfRecipe(Long recipeId, Pageable page);

    void editRecipeComment(EditCommentDto dto);

    void deleteCommentToRecipe(Long id);

    Page<CommentDto> getAllComments(Pageable page);
}
