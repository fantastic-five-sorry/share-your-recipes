package com.fantasticfour.shareyourrecipes.votes.dtos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Comment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.votes.CommentVote;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class CommentDto {

    private LocalDateTime createdAt;
    private String writerName;
    private String writerEmail;
    private String photoUrl;
    private String content;
    private Long writerId;
    private Long id;

    private Long upVoteCount;
    private Long downVoteCount;

    public CommentDto() {
    }

    public CommentDto(Comment comment) {

        User user = comment.getCreator();
        Instant instant = Instant.ofEpochMilli(comment.getCreatedDate().getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        this.createdAt = ldt;
        this.writerName = user.getName();
        this.writerEmail = user.getEmail();
        this.photoUrl = user.getPhotoUrl();
        this.content = comment.getContent();
        this.writerId = user.getId();
        this.id = comment.getId();
        this.upVoteCount = comment.getUpVoteCount();
        this.downVoteCount = comment.getDownVoteCount();
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWriterName() {
        return this.writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterEmail() {
        return this.writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getWriterId() {
        return this.writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
