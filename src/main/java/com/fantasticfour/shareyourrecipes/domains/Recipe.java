package com.fantasticfour.shareyourrecipes.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// import com.fantasticfour.shareyourrecipes.domains.User;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "recipes", schema = "public")

public class Recipe extends AuditModel {
    @Id
    @GeneratedValue(generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String title;

    private String image;

    @ElementCollection
    private Map<String, String> ingredients;

    @ElementCollection
    private List<String> steps;

    private String guideVideoUrl;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonBackReference
    private User creator;

    @OneToMany(mappedBy = "creator")
    private List<Comment> comments = new ArrayList<>();
    private String status;

    private Float price;

    private String slug;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getGuideVideoUrl() {
        return this.guideVideoUrl;
    }

    public void setGuideVideoUrl(String guideVideoUrl) {
        this.guideVideoUrl = guideVideoUrl;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Recipe() {
    }

}
