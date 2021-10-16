package com.fantasticfour.shareyourrecipes.domains.recipes;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
@Table(name = "purchased_recipes", schema = "public")
public class PurchasedRecipe extends AuditModel {
    @EmbeddedId
    private PurchasedRecipeId id;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime purchasedAt;

    @MapsId("recipeId")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonBackReference
    private Recipe recipe;

    @MapsId("creatorId")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonBackReference
    private User creator;

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return this.creator;
    }

    public void setUser(User user) {
        this.creator = user;
    }

    public PurchasedRecipeId getId() {
        return this.id;
    }

    public void setId(PurchasedRecipeId id) {
        this.id = id;
    }

    public LocalDateTime getPurchasedAt() {
        return this.purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

}
