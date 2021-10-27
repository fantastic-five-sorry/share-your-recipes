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
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.AnswerRepo;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.votings.dtos.VotingDto;
import com.fantasticfour.shareyourrecipes.votings.repos.AnswerVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.CommentRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.CommentVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.QuestionVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.RecipeCollectionVotingRepo;
import com.fantasticfour.shareyourrecipes.votings.repos.RecipeVotingRepo;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.VotingType;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.votings.*;

@Service
public class VotingServiceImpl implements VotingService {
    Logger logger = LoggerFactory.getLogger(VotingServiceImpl.class);

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

    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public void handleVotingToAnswer(VotingDto dto) {
        // -- Thay cac repo tuong ung, doi kieu Voting tuong ung, con lai giong nhau
        // tao voting id tu dto
        VotingId id = new VotingId(dto.getVoterId(), dto.getSubjectVotingToId());
        // tim xem user nay da vote roi hay chua
        Optional<AnswerVoting> votingOpt = answerVotingRepo.findById(id);
        // neu roi, check xem voting nay la UP hay DOWN hay UNVOTED va so sanh voi
        // [type] trong dto
        // case1: dto type == existed type (un vote)
        // case2: dto type UP != existed type DOWN (do update)
        // case3: dto type DOWN != existed type UP (do update)
        if (votingOpt.isPresent()) {
            AnswerVoting voting = votingOpt.get();
            VotingType currentState = voting.getType();
            // case 2, 3, 4
            if (!dto.getType().equals(currentState.toString())) {
                // update voting table
                try {
                    logger.info("Change Voting Type into " + VotingType.valueOf(dto.getType()).toString());
                    voting.setType(VotingType.valueOf(dto.getType()));
                    answerVotingRepo.save(voting);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                // update vote count
                if (dto.getType().equals("UP")) {
                    answerRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.DOWN))
                        answerRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    answerRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.UP))
                        answerRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                voting.setType(VotingType.DEVOTED);
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
        if (dto.getType().equals(VotingType.UP.toString()))
            answerRepo.increaseUpVoteCount(dto.getSubjectVotingToId());
        if (dto.getType().equals(VotingType.DOWN.toString()))
            answerRepo.increaseUpVoteCount(dto.getSubjectVotingToId());
    }

    @Override
    public void handleVotingToComment(VotingDto dto) {

        VotingId id = new VotingId(dto.getVoterId(), dto.getSubjectVotingToId());
        Optional<CommentVoting> votingOpt = commentVotingRepo.findById(id);

        if (votingOpt.isPresent()) {
            CommentVoting voting = votingOpt.get();
            VotingType currentState = voting.getType();
            if (!dto.getType().equals(currentState.toString())) {
                try {
                    logger.info("Change Voting Type into " + VotingType.valueOf(dto.getType()).toString());
                    voting.setType(VotingType.valueOf(dto.getType()));
                    commentVotingRepo.save(voting);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                if (dto.getType().equals("UP")) {
                    commentRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.DOWN))
                        commentRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    commentRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.UP))
                        commentRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                voting.setType(VotingType.DEVOTED);
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
        if (dto.getType().equals(VotingType.UP.toString()))
            commentRepo.increaseUpVoteCount(dto.getSubjectVotingToId());
        if (dto.getType().equals(VotingType.DOWN.toString()))
            commentRepo.increaseUpVoteCount(dto.getSubjectVotingToId());

    }

    @Override
    public void handleVotingToQuestion(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<QuestionVoting> votingOpt = questionVotingRepo.findById(id);

    }

    @Override
    @Transactional
    public void handleVotingToRecipe(VotingDto dto) {
        // -- Thay cac repo tuong ung, doi kieu Voting tuong ung, con lai giong nhau
        // tao voting id tu dto
        VotingId id = new VotingId(dto.getVoterId(), dto.getSubjectVotingToId());
        // tim xem user nay da vote roi hay chua
        Optional<RecipeVoting> votingOpt = recipeVotingRepo.findById(id);
        // neu roi, check xem voting nay la UP hay DOWN hay UNVOTED va so sanh voi
        // [type] trong dto
        // case1: dto type == existed type (un vote)
        // case2: dto type UP != existed type DOWN (do update)
        // case3: dto type DOWN != existed type UP (do update)
        if (votingOpt.isPresent()) {
            RecipeVoting voting = votingOpt.get();
            VotingType currentState = voting.getType();
            // case 2, 3, 4
            if (!dto.getType().equals(currentState.toString())) {
                // update voting table
                try {
                    logger.info("Change Voting Type into " + VotingType.valueOf(dto.getType()).toString());
                    voting.setType(VotingType.valueOf(dto.getType()));
                    recipeVotingRepo.save(voting);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage());
                }
                // update vote count
                if (dto.getType().equals("UP")) {
                    recipeRepo.increaseUpVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.DOWN))
                        recipeRepo.decreaseDownVoteCount(id.getSubjectId());
                }
                if (dto.getType().equals("DOWN")) {
                    recipeRepo.increaseDownVoteCount(id.getSubjectId());
                    if (currentState.equals(VotingType.UP))
                        recipeRepo.decreaseUpVoteCount(id.getSubjectId());
                }
                return;

            }
            try {
                logger.info("Change Voting Type into DEVOTED");
                voting.setType(VotingType.DEVOTED);
                recipeVotingRepo.save(voting);

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
        if (dto.getType().equals(VotingType.UP.toString()))
            recipeRepo.increaseUpVoteCount(dto.getSubjectVotingToId());
        if (dto.getType().equals(VotingType.DOWN.toString()))
            recipeRepo.increaseUpVoteCount(dto.getSubjectVotingToId());

    }

    @Override
    public void handleVotingToRecipeCollection(VotingDto voting) {
        VotingId id = new VotingId(voting.getVoterId(), voting.getSubjectVotingToId());
        Optional<RecipeCollectionVoting> votingOpt = recipeCollectionVotingRepo.findById(id);
    }

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
