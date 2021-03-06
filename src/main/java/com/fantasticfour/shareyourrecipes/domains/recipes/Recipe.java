package com.fantasticfour.shareyourrecipes.domains.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;

@Entity
@Table(name = "recipes", schema = "public")
@TypeDefs({ @TypeDef(name = "list-array", typeClass = ListArrayType.class),
        @TypeDef(name = "json", typeClass = JsonType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
@Indexed
public class Recipe extends AuditModel {
    @Id
    @GeneratedValue(generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.YES)
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
    private User creator;

    @OneToMany(mappedBy = "recipe")
    @OrderBy("id DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private RecipeStatus status;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false, unique = true)
    private String slug;
    @Column(nullable = false)
    private Long upVoteCount;
    @Column(nullable = false)
    private Long downVoteCount;
    @Column(nullable = false)
    private Boolean deleted;

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

    public RecipeStatus getStatus() {
        return this.status;
    }

    public void setStatus(RecipeStatus status) {
        this.status = status;
    }

    public Boolean isDeleted() {
        return this.deleted;
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
        this.deleted = false;
        this.status = RecipeStatus.PENDING;
        this.price = 0F;
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
