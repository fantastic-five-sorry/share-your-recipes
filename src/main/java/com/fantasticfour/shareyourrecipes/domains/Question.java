package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.QuestionStatus;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "questions", schema = "public")
public class Question extends AuditModel {
    @Id
    @GeneratedValue(generator = "quetion_generator")
    @SequenceGenerator(name = "quetion_generator", sequenceName = "quetion_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String slug;
    @Column(nullable = false)
    @Length(max = 100)
    private String title;
    @Column(nullable = false)
    @Length(max = 65535)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private QuestionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @OneToMany(mappedBy = "question")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Answer> answers = new ArrayList<>();

    @Column(nullable = false)
    private Boolean deleted;
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

    public Question() {
        status = QuestionStatus.PENDING;
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

    public QuestionStatus getStatus() {
        return this.status;
    }

    public void setStatus(QuestionStatus status) {
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
