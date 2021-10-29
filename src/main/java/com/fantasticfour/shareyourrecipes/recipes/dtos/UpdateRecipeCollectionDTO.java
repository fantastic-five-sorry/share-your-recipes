package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

public class UpdateRecipeCollectionDTO {
    
    private Long id;

    private String name;
    private List<Recipe> recipes;

    // private Long upVoteCount;
    // private Long downVoteCount;
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    // public Long getUpVoteCount() {
    // return this.upVoteCount;
    //

    // public void setUpVoteCount(Long upVoteCount) {
    // this.upVoteCount = upVoteCount;
    //

    // public Long getDownVoteCount() {
    // return this.downVoteCount;
    //

    // public void setDownVoteCount(Long downVoteCount) {
    // this.downVoteCount = downVoteCount;
    //

    public UpdateRecipeCollectionDTO() {
    }
}
