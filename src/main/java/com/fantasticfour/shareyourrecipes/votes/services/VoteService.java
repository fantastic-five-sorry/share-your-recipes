package com.fantasticfour.shareyourrecipes.votes.services;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.votes.*;
import com.fantasticfour.shareyourrecipes.votes.dtos.VoteDto;

public interface VoteService {
    void handleVotingToAnswer(VoteDto voting);

    void handleVotingToComment(VoteDto voting);

    void handleVotingToQuestion(VoteDto voting);

    void handleVotingToRecipe(VoteDto voting);

    void handleVotingToRecipeCollection(VoteDto voting);

    List<AnswerVote> getListVotingToAnswer(Long id);

    List<CommentVote> getListVotingToComment(Long id);

    List<QuestionVote> getListVotingToQuestion(Long id);

    List<RecipeVote> getListVotingToRecipe(Long id);

    List<RecipeCollectionVote> getListVotingToRecipeCollection(Long id);
}
