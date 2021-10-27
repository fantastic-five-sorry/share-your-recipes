package com.fantasticfour.shareyourrecipes.votings.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.votings.*;
import com.fantasticfour.shareyourrecipes.votings.dtos.VotingDto;

public interface VotingService {
    void handleVotingToAnswer(VotingDto voting);

    void handleVotingToComment(VotingDto voting);

    void handleVotingToQuestion(VotingDto voting);

    void handleVotingToRecipe(VotingDto voting);

    void handleVotingToRecipeCollection(VotingDto voting);

    List<AnswerVoting> getListVotingToAnswer(Long id);

    List<CommentVoting> getListVotingToComment(Long id);

    List<QuestionVoting> getListVotingToQuestion(Long id);

    List<RecipeVoting> getListVotingToRecipe(Long id);

    List<RecipeCollectionVoting> getListVotingToRecipeCollection(Long id);
}
