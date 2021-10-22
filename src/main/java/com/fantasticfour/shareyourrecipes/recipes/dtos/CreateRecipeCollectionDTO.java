package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.ArrayList;
import java.util.List;


public class CreateRecipeCollectionDTO {
    private String name;
    private Long creatorId;
    private List<Long> recipesId = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public List<Long> getRecipesId() {
        return this.recipesId;
    }

    public void setRecipesId(List<Long> recipeId) {
        this.recipesId = recipeId;
    }

    public CreateRecipeCollectionDTO() {}
}
