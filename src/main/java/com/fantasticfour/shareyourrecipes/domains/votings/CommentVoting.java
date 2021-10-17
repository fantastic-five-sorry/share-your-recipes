package com.fantasticfour.shareyourrecipes.domains.votings;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

@Entity
public class CommentVoting{
    @EmbeddedId
    private VotingId id;

    @MapsId("subjectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @MapsId("voterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

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

    public CommentVoting() {
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

    public CommentVoting(VotingId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }
}
