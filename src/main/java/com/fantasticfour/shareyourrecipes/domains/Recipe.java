package com.fantasticfour.shareyourrecipes.domains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "recipes", schema = "public")
@TypeDefs({ @TypeDef(name = "list-array", typeClass = ListArrayType.class),
        @TypeDef(name = "json", typeClass = JsonType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })

public class Recipe extends AuditModel {
    @Id
    @GeneratedValue(generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String title;

    private String image;

    // @ElementCollection
    // private Map<String, String> ingredients;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> ingredients = new HashMap<>();
    // @ElementCollection
    @Type(type = "list-array")
    @Column(name = "steps", columnDefinition = "text[]")
    private List<String> steps;

    private String guideVideoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
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
