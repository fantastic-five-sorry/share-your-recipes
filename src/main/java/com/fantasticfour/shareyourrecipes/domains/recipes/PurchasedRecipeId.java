package com.fantasticfour.shareyourrecipes.domains.recipes;

import java.io.Serializable;

import javax.persistence.*;

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


    public PurchasedRecipeId(Long recipeId, Long creatorId) {
        this.recipeId = recipeId;
        this.creatorId = creatorId;
    }
  

    public PurchasedRecipeId() {
    }
    
}
