package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", schema = "public")
public class User {

    public User(String email, String password, String name) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.blocked = false;
        this.enable = false;
        this.roles.add(new Role(ERole.ROLE_USER));
    }

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1000, allocationSize = 1)

    private Long id;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;
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

    private String photoURL;
    private String name;

    private Boolean blocked;
    private Boolean enable;

    public Boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean isEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    public String getPhotoURL() {
        return this.photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    private List<Recipe> recipes = new ArrayList<>();
    @OneToMany(mappedBy = "creator")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<RecipeCollection> recipeCollections = new ArrayList<>();

    public User() {
        this.blocked = false;
        this.enable = false;
    }

    public Boolean getBlocked() {
        return this.blocked;
    }

    public Boolean getEnable() {
        return this.enable;
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

}
