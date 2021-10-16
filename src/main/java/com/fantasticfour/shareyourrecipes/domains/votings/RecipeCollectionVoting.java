package com.fantasticfour.shareyourrecipes.domains.votings;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

@Entity
public class RecipeCollectionVoting extends AuditModel {
    @EmbeddedId
    private VotingId id;

    @MapsId("subjectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_collection_id", nullable = false)
    private RecipeCollection recipeCollection;

    public RecipeCollectionVoting() {

    }

    @MapsId("voterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    public User getVoter() {
        return this.voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }


    public RecipeCollection getRecipeCollection() {
        return this.recipeCollection;
    }

    public void setRecipeCollection(RecipeCollection recipeCollection) {
        this.recipeCollection = recipeCollection;
    }
    public VotingId getId() {
        return this.id;
    }

    public void setId(VotingId id) {
        this.id = id;
    }
}
