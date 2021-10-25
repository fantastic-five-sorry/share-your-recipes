package com.fantasticfour.shareyourrecipes.votings;

import java.util.List;

import com.fantasticfour.shareyourrecipes.account.UserUtils;
import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;
import com.fantasticfour.shareyourrecipes.votings.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("")
    private ResponseEntity<?> getAllComments(Pageable page) {
        try {

            return ResponseEntity.ok().body(commentService.getAllComments(page));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PostMapping("/recipe")
    private ResponseEntity<?> commentToRecipe(@RequestBody NewCommentDto comment) {
        try {

            commentService.writeCommentToRecipe(comment);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/{commentId}")
    private ResponseEntity<?> getCommentById(@PathVariable("commentId") Long commentId) {
        try {

            CommentDto comment = commentService.getComment(commentId);
            return ResponseEntity.ok().body(comment);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    private ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        try {

            commentService.deleteCommentToRecipe(commentId);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    private ResponseEntity<?> getAllCommentsToRecipe(@PathVariable("recipeId") Long recipeId, Pageable page,
            Authentication authentication) {
        try {
            // Long uid = UserUtils.getIdFromRequest(authentication)
            // .orElseThrow(() -> new IllegalStateException("User not found"));

            // list comment dto with [voting or not] field from uid
            // List<CommentDto> comments = commentService.getCommentsOfRecipe(recipeId);
            return ResponseEntity.ok().body(commentService.getCommentsOfRecipe(recipeId, page));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PatchMapping("/edit")
    private ResponseEntity<?> editCommentToRecipe(@RequestBody EditCommentDto dto) {
        try {

            commentService.editRecipeComment(dto);
            return ResponseEntity.ok().body("Oke nha");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }
}
