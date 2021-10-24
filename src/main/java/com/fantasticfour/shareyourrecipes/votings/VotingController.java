package com.fantasticfour.shareyourrecipes.votings;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
    private VotingService votingService;

    @PostMapping("/answer")
    private ResponseEntity<?> handleAnswerVoting(@Valid VotingDto dto) {
        try {
            votingService.handleVotingToAnswer(dto);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }

    @PostMapping("/recipe")
    private ResponseEntity<?> handleRecipeVoting(@RequestBody VotingDto dto) {
        try {

            votingService.handleVotingToRecipe(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/recipeCollection")
    private ResponseEntity<?> handleRecipeCollectionVoting(@Valid VotingDto dto) {
        try {

            votingService.handleVotingToRecipeCollection(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/comment")
    private ResponseEntity<?> handleCommentVoting(@Valid VotingDto dto) {
        try {

            votingService.handleVotingToComment(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @PostMapping("/question")
    private ResponseEntity<?> handleQuestionVoting(@Valid VotingDto dto) {
        try {

            votingService.handleVotingToQuestion(dto);

            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");

        }
    }

    @GetMapping("/answer/{id}")
    private List<UserInfo> getAllVotingToAnswer(@PathVariable("id") Long id) {
        return votingService.getListVotingToAnswer(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/question/{id}")
    private List<UserInfo> getAllVotingToQuestion(@PathVariable("id") Long id) {
        return votingService.getListVotingToQuestion(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe/{id}")
    private List<UserInfo> getAllVotingToRecipe(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipe(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe-collection/{id}")
    private List<UserInfo> getAllVotingToRecipeCollection(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipeCollection(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }

    @GetMapping("/comment/{id}")
    private List<UserInfo> getAllVotingToComment(@PathVariable("id") Long id) {
        return votingService.getListVotingToComment(id).stream().map(m -> new UserInfo(m.getVoter()))
                .collect(Collectors.toList());
    }
}
