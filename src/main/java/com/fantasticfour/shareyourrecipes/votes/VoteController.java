package com.fantasticfour.shareyourrecipes.votes;

import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserUtils;
import com.fantasticfour.shareyourrecipes.votes.dtos.UserVoteDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.VoteDto;
import com.fantasticfour.shareyourrecipes.votes.services.VoteService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voting")
@PreAuthorize("isAuthenticated()")
public class VoteController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(VoteController.class);
    @Autowired
    public VoteService votingService;

    @PostMapping("/answer")
    public ResponseEntity<?> handleAnswerVoting(@RequestBody VoteDto dto, Authentication authentication) {
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
    public ResponseEntity<?> handleRecipeVoting(@RequestBody VoteDto dto, Authentication authentication) {
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
    public ResponseEntity<?> handleRecipeCollectionVoting(@RequestBody VoteDto dto, Authentication authentication) {
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
    public ResponseEntity<?> handleCommentVoting(@RequestBody VoteDto dto, Authentication authentication) {
        try {
            Long uid = UserUtils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            dto.setVoterId(uid);
            votingService.handleVotingToComment(dto);
            logger.info("Voting to comment " + dto.getSubjectId());
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());

        }
    }

    @PostMapping("/question")
    public ResponseEntity<?> handleQuestionVoting(@RequestBody VoteDto dto, Authentication authentication) {
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
    public List<UserVoteDto> getAllVotingToAnswer(@PathVariable("id") Long id) {
        return votingService.getListVotingToAnswer(id).stream()
                .map(m -> new UserVoteDto(m.getVoter(), m.getId().getSubjectId(), m.getType().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/question/{id}")
    public List<UserVoteDto> getAllVotingToQuestion(@PathVariable("id") Long id) {
        return votingService.getListVotingToQuestion(id).stream()
                .map(m -> new UserVoteDto(m.getVoter(), m.getId().getSubjectId(), m.getType().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe/{id}")
    public List<UserVoteDto> getAllVotingToRecipe(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipe(id).stream()
                .map(m -> new UserVoteDto(m.getVoter(), m.getId().getSubjectId(), m.getType().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recipe-collection/{id}")
    public List<UserVoteDto> getAllVotingToRecipeCollection(@PathVariable("id") Long id) {
        return votingService.getListVotingToRecipeCollection(id).stream()
                .map(m -> new UserVoteDto(m.getVoter(), m.getId().getSubjectId(), m.getType().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/comment/{id}")
    public List<UserVoteDto> getAllVotingToComment(@PathVariable("id") Long id) {
        return votingService.getListVotingToComment(id).stream()
                .map(m -> new UserVoteDto(m.getVoter(), m.getId().getSubjectId(), m.getType().toString()))
                .collect(Collectors.toList());
    }
}
