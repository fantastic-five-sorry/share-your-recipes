package com.fantasticfour.shareyourrecipes.domains.votes;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.VoteType;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
@Table(name = "comment_votes")
public class CommentVote {
    @EmbeddedId
    private VoteId id;

    @MapsId("subjectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @MapsId("voterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private VoteType type;

    public VoteType getType() {
        return this.type;
    }

    public void setType(VoteType type) {
        this.type = type;
    }

    public Comment getComment() {
        return this.comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getVoter() {
        return this.voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public CommentVote() {
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

    public CommentVote(VoteId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }
}
