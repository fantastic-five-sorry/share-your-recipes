package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

@Entity
@Table(name = "answers", schema = "public")
public class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User answerer;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    private Long voteCount;

    public Answer() {
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

    public Long getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

}
