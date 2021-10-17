package com.fantasticfour.shareyourrecipes.votings.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.votings.AnswerVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.CommentVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.QuestionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.RecipeCollectionVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.RecipeVoting;
import com.fantasticfour.shareyourrecipes.domains.votings.VotingId;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.user.UserRepo;
import com.fantasticfour.shareyourrecipes.votings.dtos.VotingDto;
import com.fantasticfour.shareyourrecipes.votings.repos.AnswerVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.CommentVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.QuestionVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.RecipeCollectionVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.RecipeVotingRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.votings.*;

@Service
public class VotingServiceImpl implements VotingService {
    @Autowired
    AnswerVotingRepo answerVotingRepo;
    @Autowired
    CommentVotingRepo commentVotingRepo;
    @Autowired
    QuestionVotingRepo questionVotingRepo;
    @Autowired
    RecipeVotingRepo recipeVotingRepo;
    @Autowired
    RecipeCollectionVotingRepo recipeCollectionVotingRepo;

    @Autowired
    RecipeRepository recipeRepo;

    // @Autowired
    // AnswerRepo answerRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public void handleVotingToAnswer(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<AnswerVoting> votingOpt = answerVotingRepo.findById(id);

        // un-vote
        if (votingOpt.isPresent()) {
            answerVotingRepo.delete(votingOpt.get());
        }

        // AnswerVoting answerVoting = new AnswerVoting(id);
        // // // User user = userRepo.findEnabledUserById(id.getVoterId());
        // // // Answer answer = userRepo.findEnabledUserById(id.getVoterId());
        // // if (user == null) throw new IllegalStateException("User not found");
        // answerVotingRepo.save(answerVoting);
    }

    @Override
    public void handleVotingToComment(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<CommentVoting> votingOpt = commentVotingRepo.findById(id);

        // un-vote
        if (votingOpt.isPresent()) {
            commentVotingRepo.delete(votingOpt.get());
        }

        // CommentVoting answerVoting = new CommentVoting(id);

        // commentVotingRepo.save(answerVoting);
    }

    @Override
    public void handleVotingToQuestion(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<QuestionVoting> votingOpt = questionVotingRepo.findById(id);

        // un-vote
        if (votingOpt.isPresent()) {
            questionVotingRepo.delete(votingOpt.get());

        }

        // QuestionVoting questionVoting = new QuestionVoting(id);

        // questionVotingRepo.save(questionVoting);
    }

    @Override
    public void handleVotingToRecipe(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<RecipeVoting> votingOpt = recipeVotingRepo.findById(id);

        // un-vote
        if (votingOpt.isPresent()) {
            recipeRepo.decreaseVoteCount(voting.getSubjectVotingToId());
            recipeVotingRepo.delete(votingOpt.get());
            return;
        }

        recipeRepo.increaseVoteCount(voting.getSubjectVotingToId());
        recipeVotingRepo.addVoting(id.getSubjectId(), id.getVoterId());

    }

    @Override
    public void handleVotingToRecipeCollection(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<RecipeCollectionVoting> votingOpt = recipeCollectionVotingRepo.findById(id);

        // un-vote
        if (votingOpt.isPresent()) {
            recipeCollectionVotingRepo.delete(votingOpt.get());
        }

        // RecipeCollectionVoting recipeCollectionVoting = new
        // RecipeCollectionVoting(id);

        // recipeCollectionVotingRepo.save(recipeCollectionVoting);

    }

    // private <T> void handleVoting(VotingDto voting, JpaRepository<T, VotingId>
    // repository) {
    // VotingId id = new VotingId(voting.getVoterId(),
    // voting.getSubjectVotingToId());
    // Optional<T> votingOpt = repository.findById(id);

    // // un-vote
    // if (votingOpt.isPresent()) {
    // repository.delete(votingOpt.get());
    // }

    // T newT = new T(id);

    // repository.save(newT);
    // }
    @Override
    public List<AnswerVoting> getListVotingToAnswer(Long id) {

        return answerVotingRepo.findByAnswerId(id);
    }

    @Override
    public List<CommentVoting> getListVotingToComment(Long id) {

        return commentVotingRepo.findByCommentId(id);
    }

    @Override
    public List<QuestionVoting> getListVotingToQuestion(Long id) {

        return questionVotingRepo.findByQuestionId(id);
    }

    @Override
    public List<RecipeVoting> getListVotingToRecipe(Long id) {

        return recipeVotingRepo.findByRecipeId(id);
    }

    @Override
    public List<RecipeCollectionVoting> getListVotingToRecipeCollection(Long id) {

        return recipeCollectionVotingRepo.findByRecipeCollectionId(id);
    }
}
