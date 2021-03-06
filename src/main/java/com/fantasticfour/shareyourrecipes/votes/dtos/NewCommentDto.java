package com.fantasticfour.shareyourrecipes.votes.dtos;

import javax.validation.constraints.NotBlank;

public class NewCommentDto {
    
    private Long writerId;

    @NotBlank
    private Long recipeId;

    @NotBlank
    private String content;

    public Long getWriterId() {
        return this.writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
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

    public NewCommentDto() {
    }


    @Override
    public String toString() {
        return "{" +
            " writerId='" + getWriterId() + "'" +
            ", recipeId='" + getRecipeId() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }

}
