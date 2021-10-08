package com.fantasticfour.shareyourrecipes.domains;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Embeddable
public class PurchasedRecipeId implements Serializable {

    private Long recipeId;
    private Long creatorId;

    public Long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    // @MapsId("recipeId")
    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinColumn(name = "recipe_id", nullable = false)
    // @JsonBackReference
    // private Recipe recipe;

    // @MapsId("creatorId")
    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinColumn(name = "creator_id", nullable = false)
    // @JsonBackReference
    // private User creator;

    // public PurchasedRecipeId() {
    // }

    // public Recipe getRecipe() {
    // return this.recipe;
    // }

    // public void setRecipe(Recipe recipe) {
    // this.recipe = recipe;
    // }

    // public User getUser() {
    // return this.creator;
    // }

    // public void setUser(User user) {
    // this.creator = user;
    // }


    public PurchasedRecipeId() {
    }
    
}
