package com.fantasticfour.shareyourrecipes.domains.recipes;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
// import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "recipes_collection", schema = "public")
public class RecipeCollection extends AuditModel {

    @Id
    @GeneratedValue(generator = "recipe_collection_generator")
    @SequenceGenerator(name = "recipe_collection_generator", sequenceName = "recipe_collection_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(unique = true)
    private String slug;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "collection_recipes", joinColumns = @JoinColumn(name = "collection_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Recipe> recipes = new ArrayList<>();

    @Column(nullable = false)
    private Long upVoteCount;
    @Column(nullable = false)
    private Long downVoteCount;

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

    private Boolean deleted;

    public Boolean isDeleted() {
        return this.deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public RecipeCollection() {
        this.deleted = false;
        this.upVoteCount = 0L;
        this.downVoteCount = 0L;
    }

    public void decreaseUpVoteCount() {
        this.upVoteCount--;
    }

    public void increaseUpVoteCount() {
        this.upVoteCount++;
    }

    public void decreaseDownVoteCount() {
        this.downVoteCount--;
    }

    public void increaseDownVoteCount() {
        this.downVoteCount++;
    }
}
