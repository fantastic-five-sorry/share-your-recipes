package com.fantasticfour.shareyourrecipes.domains.votes;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.VoteType;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
@Table(name = "question_votes")

public class QuestionVote {
    @EmbeddedId
    private VoteId id;

    @MapsId("subjectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private VoteType type;

    public VoteType getType() {
        return this.type;
    }

    public void setType(VoteType type) {
        this.type = type;
    }

    public QuestionVote() {
    }

    @MapsId("voterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    public User getVoter() {
        return this.voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VoteId getId() {
        return this.id;
    }

    public void setId(VoteId id) {
        this.id = id;
    }

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public QuestionVote(VoteId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

}
