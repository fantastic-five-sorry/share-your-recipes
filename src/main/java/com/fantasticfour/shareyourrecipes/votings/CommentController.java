package com.fantasticfour.shareyourrecipes.votings;

import com.fantasticfour.shareyourrecipes.account.UserUtils;
import com.fantasticfour.shareyourrecipes.votings.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votings.dtos.NewCommentDto;
import com.fantasticfour.shareyourrecipes.votings.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public CommentService commentService;

    @GetMapping("")
    public ResponseEntity<?> getAllComments(Pageable page) {
        try {

            return ResponseEntity.ok().body(commentService.getAllComments(page));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PostMapping("/recipe")
    public ResponseEntity<?> commentToRecipe(@RequestBody NewCommentDto comment) {
        try {

            commentService.writeCommentToRecipe(comment);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable("commentId") Long commentId) {
        try {

            CommentDto comment = commentService.getComment(commentId);
            return ResponseEntity.ok().body(comment);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        try {

            commentService.deleteCommentToRecipe(commentId);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/recipe2/{recipeId}")
    public ResponseEntity<?> getAllCommentsToRecipe(@PathVariable("recipeId") Long recipeId, Pageable page,
            Authentication authentication) {
        try {

            Page<CommentDto> pageDto = commentService.getCommentsOfRecipe(recipeId, page);
            return ResponseEntity.ok().body(pageDto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> getCommentVotingsOfRecipe(@PathVariable("recipeId") Long recipeId, Pageable page,
            Authentication authentication) {
        try {

            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            return ResponseEntity.ok().body(commentService.getCommentVotingsOfRecipe(recipeId, uid, page));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> editCommentToRecipe(@RequestBody EditCommentDto dto) {
        try {

            commentService.editRecipeComment(dto);
            return ResponseEntity.ok().body("Oke nha");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }
}
