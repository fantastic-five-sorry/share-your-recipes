package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

public class RecipeCollectionDTO {


    private String name;

    private UserInfo creator;
    private List<RecipeDTO> recipes = new ArrayList<>();

    private Long voteCount;
    private String slug;

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfo getCreator() {
        return this.creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }

    public List<RecipeDTO> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public Long getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public RecipeCollectionDTO() {}

    public RecipeCollectionDTO(RecipeCollection collection) {
        this.creator =  new UserInfo(collection.getCreator());
        this.name = collection.getName();
        this.recipes = collection.getRecipes().stream().map(RecipeDTO::new).collect(Collectors.toList());
        this.voteCount = collection.getVoteCount();
        this.slug  =  collection.getSlug();
    }
    
}
