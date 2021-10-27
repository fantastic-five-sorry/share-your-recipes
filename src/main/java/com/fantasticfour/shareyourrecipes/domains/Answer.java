package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "answers", schema = "public")
public class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User answerer;

    @Column(nullable = false)
    @Length(max = 65535)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
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

    public Answer() {
        this.deleted = false;
        this.upVoteCount = 0L;
        this.downVoteCount = 0L;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAnswerer() {
        return this.answerer;
    }

    public void setAnswerer(User answerer) {
        this.answerer = answerer;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    // @Column(nullable = false, columnDefinition = "TIMESTAMP")
    // @Convert(converter = LocalDateTimeConverter.class)
    // private LocalDateTime createdAt;
}
