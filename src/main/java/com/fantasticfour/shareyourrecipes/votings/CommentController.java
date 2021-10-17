package com.fantasticfour.shareyourrecipes.votings;

import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;
import com.fantasticfour.shareyourrecipes.votings.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/recipe")
    private ResponseEntity<?> commentToRecipe(@RequestBody NewCommentDto comment) {
        try {

            commentService.writeCommentToRecipe(comment);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @DeleteMapping("/recipe")
    private ResponseEntity<?> deleteCommentToRecipe(@RequestBody NewCommentDto comment) {
        try {

            commentService.writeCommentToRecipe(comment);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    private ResponseEntity<?> deleteCommentToRecipe(@PathVariable("recipeId") Long recipeId) {
        try {

            commentService.getCommentsOfRecipe(recipeId);
            return ResponseEntity.ok().body(commentService.getCommentsOfRecipe(recipeId));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }
}
