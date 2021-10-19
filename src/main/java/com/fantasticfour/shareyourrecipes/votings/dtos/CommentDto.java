package com.fantasticfour.shareyourrecipes.votings.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Comment;

public class CommentDto {

    private UserInfo writer;

    @NotBlank
    private Long recipeId;

    @NotBlank
    private String content;

    private Date createdAt;

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
        this.writer = new UserInfo(comment.getCreator());
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedDate();
        this.recipeId = comment.getRecipe().getId();
    }
}
