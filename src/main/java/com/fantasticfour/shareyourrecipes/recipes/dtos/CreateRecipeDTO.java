package com.fantasticfour.shareyourrecipes.recipes.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateRecipeDTO {
    @NotBlank
    private String title;

    private String image;

    @NotNull
    private Map<String, String> ingredients = new HashMap<>();
    @NotNull
    private List<String> steps;
    private String guideVideoString;
    private Long creatorId;

    @NotBlank
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

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public CreateRecipeDTO() {
    }

}
