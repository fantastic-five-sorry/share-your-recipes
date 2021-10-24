package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "questions", schema = "public")
public class Question extends AuditModel {
    @Id
    @GeneratedValue(generator = "quetion_generator")
    @SequenceGenerator(name = "quetion_generator", sequenceName = "quetion_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;
    private String slug;
    private String title;
    private String content;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();
    private Boolean deleted;

    private Long upVoteCount;
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

    public Question() {
        deleted = false;
        this.upVoteCount = 0L;
        this.downVoteCount = 0L;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Boolean isDeleted() {
        return this.deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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
