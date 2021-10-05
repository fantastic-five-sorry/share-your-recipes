package com.fantasticfour.shareyourrecipes.domains;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "purchased_recipes", schema = "public")
public class PurchasedRecipe {
    @EmbeddedId
    private PurchasedRecipeId id;

    private Date purchasedAt;
}
