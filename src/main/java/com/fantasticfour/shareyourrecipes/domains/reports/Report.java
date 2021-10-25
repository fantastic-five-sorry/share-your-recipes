package com.fantasticfour.shareyourrecipes.domains.reports;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;

@Entity
@Table(name = "reports", schema = "public")
public class Report {

    @Id
    @GeneratedValue(generator = "report_generator")
    @SequenceGenerator(name = "report_generator", sequenceName = "report_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getReporter() {
        return this.reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    private LocalDateTime createdAt;


    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Report() {
    }

    

}

// handle : report_id + name + status;
