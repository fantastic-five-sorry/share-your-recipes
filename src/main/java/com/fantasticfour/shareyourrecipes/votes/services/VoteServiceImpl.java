package com.fantasticfour.shareyourrecipes.votes.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.votes.dtos.VoteDto;
import com.fantasticfour.shareyourrecipes.votes.repos.AnswerVoteRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.CommentRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.CommentVoteRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.QuestionVoteRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.RecipeCollectionVoteRepo;
import com.fantasticfour.shareyourrecipes.votes.repos.RecipeVoteRepo;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasticfour.shareyourrecipes.account.UserRepo;

import com.fantasticfour.shareyourrecipes.domains.enums.VoteType;
import com.fantasticfour.shareyourrecipes.domains.votes.*;

@Service
public class VoteServiceImpl implements VoteService {
    Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    @Autowired
    AnswerVoteRepo answerVotingRepo;
    @Autowired
    CommentVoteRepo commentVotingRepo;
    @Autowired
    QuestionVoteRepo questionVotingRepo;
    @Autowired
    RecipeVoteRepo recipeVotingRepo;
    @Autowired
    RecipeCollectionVoteRepo recipeCollectionVotingRepo;

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public void handleVotingToAnswer(VoteDto dto) {
        // -- Thay cac repo tuong ung, doi kieu Voting tuong ung, con lai giong nhau
        // tao voting id tu dto
        VoteId id = new VoteId(dto.getVoterId(), dto.getSubjectId());
        // tim xem user nay da vote roi hay chua
        Optional<AnswerVote> voteOptional = answerVotingRepo.findById(id);
        // neu roi, check xem voting nay la UP hay DOWN hay UNVOTED va so sanh voi
        // [type] trong dto
        // case1: dto type == existed type (un vote)
        // case2: dto type UP != existed type DOWN (do update)
        // case3: dto type DOWN != existed type UP (do update)
        if (voteOptional.isPresent()) {
            AnswerVote voting = voteOptional.get();
            VoteType currentState = voting.getType();
            // case 2, 3, 4
            if (!dto.getType().equals(currentState.toString())) {
                // update voting table
                try {
                    logger.info("Change Voting Type into " + VoteType.valueOf(dto.getType()).toString());
                    voting.setType(VoteType.valueOf(dto.getType()));
                    answerVotingRepo.save(voting);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                // update vote count
                if (dto.getType().equals("UP")) {
                    answerRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.DOWN))
                        answerRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    answerRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.UP))
                        answerRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                voting.setType(VoteType.DEVOTED);
                answerVotingRepo.save(voting);

            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage());
            }
            // case 1 un vote
            if (dto.getType().equals("UP")) {
                answerRepo.decreaseUpVoteCount(id.getSubjectId());
            }
            if (dto.getType().equals("DOWN")) {
                answerRepo.decreaseDownVoteCount(id.getSubjectId());
            }

            return;
        }
        // add voting to voting table
        // neu khong ton tai (moi voting lan dau tien) thi add voting
        try {
            answerVotingRepo.addVoting(id.getSubjectId(), id.getVoterId(), dto.getType());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        // inc up or down vote
        if (dto.getType().equals(VoteType.UP.toString()))
            answerRepo.increaseUpVoteCount(dto.getSubjectId());
        if (dto.getType().equals(VoteType.DOWN.toString()))
            answerRepo.increaseUpVoteCount(dto.getSubjectId());
    }

    @Override
    public void handleVotingToComment(VoteDto dto) {

        VoteId id = new VoteId(dto.getVoterId(), dto.getSubjectId());
        Optional<CommentVote> voteOptional = commentVotingRepo.findById(id);

        if (voteOptional.isPresent()) {
            CommentVote voting = voteOptional.get();
            VoteType currentState = voting.getType();
            if (!dto.getType().equals(currentState.toString())) {
                try {
                    logger.info("Change Voting Type into " + VoteType.valueOf(dto.getType()).toString());
                    voting.setType(VoteType.valueOf(dto.getType()));
                    commentVotingRepo.save(voting);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                if (dto.getType().equals("UP")) {
                    commentRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.DOWN))
                        commentRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    commentRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.UP))
                        commentRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                voting.setType(VoteType.DEVOTED);
                commentVotingRepo.save(voting);

            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage());
            }
            // case 1 un vote
            if (dto.getType().equals("UP")) {
                commentRepo.decreaseUpVoteCount(id.getSubjectId());
            }
            if (dto.getType().equals("DOWN")) {
                commentRepo.decreaseDownVoteCount(id.getSubjectId());
            }

            return;
        }
        // add voting to voting table
        // neu khong ton tai (moi voting lan dau tien) thi add voting
        try

        {
            commentVotingRepo.addVoting(id.getSubjectId(), id.getVoterId(), dto.getType());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        // inc up or down vote
        if (dto.getType().equals(VoteType.UP.toString()))
            commentRepo.increaseUpVoteCount(dto.getSubjectId());
        if (dto.getType().equals(VoteType.DOWN.toString()))
            commentRepo.increaseUpVoteCount(dto.getSubjectId());

    }

    @Override
    public void handleVotingToQuestion(VoteDto voting) {
        VoteId id = new VoteId(voting.getVoterId(), voting.getSubjectId());
        Optional<QuestionVote> voteOptional = questionVotingRepo.findById(id);

    }

    @Override
    @Transactional
    public void handleVotingToRecipe(VoteDto dto) {
        // -- Thay cac repo tuong ung, doi kieu Voting tuong ung, con lai giong nhau
        // tao voting id tu dto
        VoteId id = new VoteId(dto.getVoterId(), dto.getSubjectId());
        // tim xem user nay da vote roi hay chua
        Optional<RecipeVote> voteOptional = recipeVotingRepo.findById(id);
        // neu roi, check xem voting nay la UP hay DOWN hay UNVOTED va so sanh voi
        // [type] trong dto
        // case1: dto type == existed type (un vote)
        // case2: dto type UP != existed type DOWN (do update)
        // case3: dto type DOWN != existed type UP (do update)
        if (voteOptional.isPresent()) {
            RecipeVote vote = voteOptional.get();
            VoteType currentState = vote.getType();
            // case 2, 3, 4
            if (!dto.getType().equals(currentState.toString())) {
                // update voting table
                try {
                    logger.info("Change Voting Type into " + VoteType.valueOf(dto.getType()).toString());
                    vote.setType(VoteType.valueOf(dto.getType()));
                    recipeVotingRepo.save(vote);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                // update vote count
                if (dto.getType().equals("UP")) {
                    recipeRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.DOWN))
                        recipeRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    recipeRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VoteType.UP))
                        recipeRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                vote.setType(VoteType.DEVOTED);
                recipeVotingRepo.save(vote);

            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage());
            }
            // case 1 un vote
            if (dto.getType().equals("UP")) {
                recipeRepo.decreaseUpVoteCount(id.getSubjectId());
            }
            if (dto.getType().equals("DOWN")) {
                recipeRepo.decreaseDownVoteCount(id.getSubjectId());
            }

            return;
        }
        // add voting to voting table
        // neu khong ton tai (moi voting lan dau tien) thi add voting
        try

        {
            recipeVotingRepo.addVoting(id.getSubjectId(), id.getVoterId(), dto.getType());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        // inc up or down vote
        if (dto.getType().equals(VoteType.UP.toString()))
            recipeRepo.increaseUpVoteCount(dto.getSubjectId());
        if (dto.getType().equals(VoteType.DOWN.toString()))
            recipeRepo.increaseUpVoteCount(dto.getSubjectId());

    }

    @Override
    public void handleVotingToRecipeCollection(VoteDto voting) {
        VoteId id = new VoteId(voting.getVoterId(), voting.getSubjectId());
        Optional<RecipeCollectionVote> voteOptional = recipeCollectionVotingRepo.findById(id);
    }

    @Override
    public List<AnswerVote> getListVotingToAnswer(Long id) {

        return answerVotingRepo.findByAnswerId(id);
    }

    @Override
    public List<CommentVote> getListVotingToComment(Long id) {

        return commentVotingRepo.findByCommentId(id);
    }

    @Override
    public List<QuestionVote> getListVotingToQuestion(Long id) {

        return questionVotingRepo.findByQuestionId(id);
    }

    @Override
    public List<RecipeVote> getListVotingToRecipe(Long id) {

        return recipeVotingRepo.findByRecipeId(id);
    }

    @Override
    public List<RecipeCollectionVote> getListVotingToRecipeCollection(Long id) {

        return recipeCollectionVotingRepo.findByRecipeCollectionId(id);
    }

}
