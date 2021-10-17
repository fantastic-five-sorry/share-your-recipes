package com.fantasticfour.shareyourrecipes.domains.votings;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.auth.User;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
public class AnswerVoting {
    @EmbeddedId
    private VotingId id;

    @MapsId("subjectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @MapsId("voterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    public Answer getAnswer() {
        return this.answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public AnswerVoting() {
        super();
    }

    public User getVoter() {
        return this.voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public VotingId getId() {
        return this.id;
    }

    public void setId(VotingId id) {
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

    public AnswerVoting(VotingId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

}
