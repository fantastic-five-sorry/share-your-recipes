package com.fantasticfour.shareyourrecipes.votes.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.votes.CommentVote;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class CommentDto {

    private Long id;
    private UserInfo writer;

    @NotBlank
    private Long recipeId;

    @NotBlank
    private String content;

    private Date createdAt;

    private Long upVoteCount;
    private Long downVoteCount;

    public CommentDto() {
    }

    public UserInfo getWriter() {
        return this.writer;
    }

    public void setWriter(UserInfo writer) {
        this.writer = writer;
    }

    public Long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.writer = new UserInfo(comment.getCreator());
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedDate();
        this.recipeId = comment.getRecipe().getId();
        this.upVoteCount = comment.getUpVoteCount();
        this.downVoteCount = comment.getDownVoteCount();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUpVoteCount() {
        return this.upVoteCount;
    }

    public void setUpVoteCount(Long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public Long getDownVoteCount() {
        return this.downVoteCount;
    }

    public void setDownVoteCount(Long downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

}
