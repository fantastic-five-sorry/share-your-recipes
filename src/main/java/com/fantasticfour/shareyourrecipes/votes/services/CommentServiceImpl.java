package com.fantasticfour.shareyourrecipes.votes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.votes.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.CommentVoteDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.EditCommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.NewCommentDto;
import com.fantasticfour.shareyourrecipes.votes.repos.CommentRepo;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RecipeRepository recipeRepo;
    final EntityManager entityManager;
    final CriteriaBuilder criteriaBuilder;

    @Autowired
    public CommentServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public void writeCommentToRecipe(NewCommentDto comment) {
        User user = userRepo.findValidUserById(comment.getWriterId());
        Recipe recipe = recipeRepo.findById(comment.getRecipeId())
                .orElseThrow(() -> new IllegalStateException("Recipe not found"));
        if (user == null)
            throw new IllegalStateException("User not found");

        Comment newComment = new Comment();

        newComment.setContent(comment.getContent());
        newComment.setCreator(user);
        newComment.setRecipe(recipe);

        commentRepo.save(newComment);
    }

    @Override
    public Page<CommentVoteDto> getCommentVotingsOfRecipe(Long recipeId, Long voterId, Pageable page) {
        Query query = entityManager.createNativeQuery(
                "select k1.id as commentId, k1.creator_id, k1.name, k1.email, k1.photo_url, k1.created_at, k1.content, k1.up_vote_count, k1.down_vote_count, k2.type from \n"
                        + "(SELECT c.*, u.name, u.photo_url, u.email \n" + "from comments c \n"
                        + "inner join users u \n" + "on c.creator_id = u.id \n"
                        + "where c.recipe_id=:recipeId) as k1 \n" + "left join \n" + "(select v.voter_id, v.type, \n"
                        + "v.comment_id \n" + "from comment_votes v \n" + "where v.voter_id=:voterId) as k2 \n"
                        + "on k1.id=k2.comment_id \n" + "order by k1.id desc \n",
                Tuple.class).setParameter("recipeId", recipeId).setParameter("voterId", voterId);

        Long totalRows = ((java.math.BigInteger) entityManager
                .createNativeQuery("select  count(k1.*) from \n" + "(SELECT c.id \n" + "from comments c \n"
                        + "where c.recipe_id=:recipeId) as k1 \n" + "left join \n" + "(select v.comment_id \n"
                        + "from comment_voting v  \n" + "where v.voter_id=:voterId) as k2 \n"
                        + "on k1.id=k2.comment_id")
                .setParameter("recipeId", recipeId).setParameter("voterId", voterId).getSingleResult()).longValue();
        logger.info(page.getPageNumber() + " : " + page.getPageSize());
        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        List<Tuple> tuples = query.getResultList();
        List<CommentVoteDto> results = tuples.stream().map(t -> {
            LocalDateTime createdAt = t.get("created_at", java.sql.Timestamp.class).toLocalDateTime();
            String creatorName = t.get("name", String.class);
            String creatorEmail = t.get("email", String.class);
            String photoUrl = t.get("photo_url", String.class);
            String commentContent = t.get("content", String.class);
            String type = t.get("type", String.class);
            Long creatorId = t.get("creator_id", java.math.BigInteger.class).longValue();
            Long commentId = t.get("commentId", java.math.BigInteger.class).longValue();
            Long upVoteCount = t.get("up_vote_count", java.math.BigInteger.class).longValue();
            Long downVoteCount = t.get("down_vote_count", java.math.BigInteger.class).longValue();
            return new CommentVoteDto(createdAt, creatorName, creatorEmail, photoUrl, commentContent, type, creatorId,
                    commentId, upVoteCount, downVoteCount);
        }).collect(Collectors.toList());

        return new PageImpl<CommentVoteDto>(results, page, totalRows);
    }

    @Override
    public Page<CommentDto> getCommentsOfRecipe(Long recipeId, Pageable page) {
        return commentRepo.findByRecipeId(recipeId, page).map(CommentDto::new);
    }

    @Override
    public void editRecipeComment(EditCommentDto dto) {

        Comment comment = commentRepo.findById(dto.getId())
                .orElseThrow(() -> new IllegalStateException("Comment not found"));

        if (!dto.getNewContent().equals(comment.getContent())) {
            comment.setContent(dto.getNewContent());
            commentRepo.save(comment);
            return;
        }
        throw new IllegalStateException("Comment has not any change");

    }

    @Override
    public void deleteCommentToRecipe(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto getComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new IllegalStateException("Comment not found"));
        return new CommentDto(comment);
    }

    @Override
    public Page<CommentDto> getAllComments(Pageable page) {
        return commentRepo.findAll(page).map(CommentDto::new);
    }
}
