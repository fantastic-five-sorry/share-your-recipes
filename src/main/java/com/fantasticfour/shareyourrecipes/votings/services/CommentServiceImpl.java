package com.fantasticfour.shareyourrecipes.votings.services;

import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.user.UserRepo;
import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;
import com.fantasticfour.shareyourrecipes.votings.repos.CommentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RecipeRepository recipeRepo;

    @Override
    public void writeCommentToRecipe(NewCommentDto comment) {
        User user = userRepo.findEnabledUserById(comment.getWriterId());
        Recipe recipe = recipeRepo.findById(comment.getRecipeId())
                .orElseThrow(() -> new IllegalStateException("Recipe not found"));
        if (user == null)
            throw new IllegalStateException("User not found");

        Comment newComment = new Comment();

        newComment.setContent(comment.getContent());
        newComment.setCreator(user);
        newComment.setRecipe(recipe);

        commentRepo.save(newComment);
    }

    @Override
    public List<CommentDto> getCommentsOfRecipe(Long recipeId) {
        return commentRepo.findByRecipeId(recipeId).stream().map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());

    }
}
