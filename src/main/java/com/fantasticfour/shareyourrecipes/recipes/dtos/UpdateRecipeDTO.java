package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.List;
import java.util.Map;

import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;

public class UpdateRecipeDTO {
    private String title;
    private String image;
    private Map<String, String> ingredients;
    private List<String> steps;
    private String guideVideoUrl;
    private RecipeStatus status;

    private Float price;

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

    public Map<String,String> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(Map<String,String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return this.steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String guideVideoUrl() {
        return this.guideVideoUrl;
    }

    public void setguideVideoUrl(String guideVideoUrl) {
        this.guideVideoUrl = guideVideoUrl;
    }

    public RecipeStatus getStatus() {
        return this.status;
    }

    public void setStatus(RecipeStatus status) {
        this.status = status;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    // private Long upVoteCount;
    // private Long downVoteCount;

    UpdateRecipeDTO() {
        
    }
}
