package com.fantasticfour.shareyourrecipes.votings.dtos;

import java.time.LocalDateTime;

public class CommentVotingDto {
    private LocalDateTime createdAt;
    private String writerName;
    private String writerEmail;
    private String photoUrl;
    private String content;
    private String type;
    private Long writerId;
    private Long id;

    public CommentVotingDto() {
    }

    public CommentVotingDto(LocalDateTime createdAt, String creatorName, String creatorEmail, String photoUrl,
            String commentContent, String type, Long creatorId, Long commentId) {
        this.createdAt = createdAt;
        this.writerName = creatorName;
        this.writerEmail = creatorEmail;
        this.photoUrl = photoUrl;
        this.content = commentContent;
        this.type = type;
        this.writerId = creatorId;
        this.id = commentId;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

}
