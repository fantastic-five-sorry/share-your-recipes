package com.fantasticfour.shareyourrecipes.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.votes.CommentVote;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "comments", schema = "public")
public class Comment extends AuditModel {

    @Id
    @GeneratedValue(generator = "comment_generator")
    @SequenceGenerator(name = "comment_generator", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @Length(max = 65535)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private Long upVoteCount;
    @Column(nullable = false)
    private Long downVoteCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CommentVote> votes = new ArrayList<>();

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

    public Comment() {
        upVoteCount = 0L;
        downVoteCount = 0L;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Long getVoteCount() {
        return this.upVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.upVoteCount = voteCount;
    }

}
