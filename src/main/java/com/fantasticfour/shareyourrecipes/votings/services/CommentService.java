package com.fantasticfour.shareyourrecipes.votings.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;

public interface CommentService {
    void writeCommentToRecipe(NewCommentDto comment);

    CommentDto getComment(Long id);

    List<CommentDto> getCommentsOfRecipe(Long recipeId);

    void editRecipeComment(EditCommentDto dto);

    void deleteCommentToRecipe(Long id);
}
