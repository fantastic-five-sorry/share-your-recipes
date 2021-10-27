package com.fantasticfour.shareyourrecipes.votings;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fantasticfour.shareyourrecipes.account.UserUtils;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.votings.AnswerVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.CommentVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.QuestionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.RecipeCollectionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.RecipeVoting;
import com.fantasticfour.shareyourrecipes.votings.dtos.VotingDto;
import com.fantasticfour.shareyourrecipes.votings.services.VotingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voting")
public class VotingController {
    @Autowired
    public VotingService votingService;

    @PostMapping("/answer")
    public ResponseEntity<?> handleAnswerVoting(@RequestBody VotingDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);

            votingService.handleVotingToAnswer(dto);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PostMapping("/recipe")
    public ResponseEntity<?> handleRecipeVoting(@RequestBody VotingDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);

            votingService.handleVotingToRecipe(dto);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/recipeCollection")
    public ResponseEntity<?> handleRecipeCollectionVoting(@RequestBody VotingDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);
            votingService.handleVotingToRecipeCollection(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> handleCommentVoting(@RequestBody VotingDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);
            votingService.handleVotingToComment(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/question")
    public ResponseEntity<?> handleQuestionVoting(@RequestBody VotingDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);

            votingService.handleVotingToQuestion(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @GetMapping("/answer/{id}")
    public List<UserInfo> getAllVotingToAnswer(@PathVariable("id") Long id) {
        return votingService.getListVotingToAnswer(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/question/{id}")
    public List<UserInfo> getAllVotingToQuestion(@PathVariable("id") Long id) {
        return votingService.getListVotingToQuestion(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe/{id}")
    public List<UserInfo> getAllVotingToRecipe(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipe(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe-collection/{id}")
    public List<UserInfo> getAllVotingToRecipeCollection(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipeCollection(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/comment/{id}")
    public List<UserInfo> getAllVotingToComment(@PathVariable("id") Long id) {
        return votingService.getListVotingToComment(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }
}
