package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

public class RecipeDTO {
    private String title;
    private String image;
    private Map<String, String> ingredients = new HashMap<>();
    private List<String> steps;
    private String guideVideoString;
    private UserInfo creator;
    private String slug;

    private List<Comment> comments = new ArrayList<>();
    private String status;

    private Float price;
    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, String> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return this.steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getGuideVideoString() {
        return this.guideVideoString;
    }

    public void setGuideVideoString(String guideVideoString) {
        this.guideVideoString = guideVideoString;
    }

    public UserInfo getCreator() {
        return this.creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public RecipeDTO() {
    }

    public RecipeDTO(Recipe recipe) {
        this.title = recipe.getTitle();
        this.image = recipe.getImage();
        this.ingredients = recipe.getIngredients();
        this.steps = recipe.getSteps();
        this.guideVideoString = recipe.getGuideVideoUrl();
        this.creator = new UserInfo(recipe.getCreator());
        this.slug = recipe.getSlug();
    }


    
}
