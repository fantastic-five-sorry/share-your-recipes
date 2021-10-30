package com.fantasticfour.shareyourrecipes.domains.auth;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.recipes.RecipeCollection;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {

    public User(String email, String password, String name) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.blocked = false;
        this.enabled = false;
        this.provider = Provider.local;
    }

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    // @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Provider provider;

    private String providerId;

    @Column(unique = true)
    private String email;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String photoUrl;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean blocked;

    @Column(nullable = false)
    private Boolean enabled;

    public Boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enable) {
        this.enabled = enable;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeCollection> getRecipeCollections() {
        return this.recipeCollections;
    }

    public void setRecipeCollections(List<RecipeCollection> recipeCollections) {
        this.recipeCollections = recipeCollections;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Recipe> recipes = new ArrayList<>();
    @OneToMany(mappedBy = "creator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<RecipeCollection> recipeCollections = new ArrayList<>();

    public User() {
        this.name = "Default Name";
        this.blocked = false;
        this.enabled = false;
        this.provider = Provider.local;
    }

    public Boolean getBlocked() {
        return this.blocked;
    }

    public Boolean getEnable() {
        return this.enabled;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
