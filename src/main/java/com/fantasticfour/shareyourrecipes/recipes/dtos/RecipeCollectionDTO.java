package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

public class RecipeCollectionDTO {
    private Long id;

    
    private String name;

    private UserInfo creator;
    private List<RecipeDTO> recipes = new ArrayList<>();


    private String slug;
    private Long upVoteCount;
    private Long downVoteCount;

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

  

    public RecipeCollectionDTO() {}

    public RecipeCollectionDTO(RecipeCollection collection) {
        this.id = collection.getId();
        this.creator =  new UserInfo(collection.getCreator());
        this.name = collection.getName();
        this.recipes = collection.getRecipes().stream().map(RecipeDTO::new).collect(Collectors.toList());
        this.upVoteCount = collection.getUpVoteCount();
        this.downVoteCount = collection.getDownVoteCount();
        this.slug  =  collection.getSlug();
    }
    
}
