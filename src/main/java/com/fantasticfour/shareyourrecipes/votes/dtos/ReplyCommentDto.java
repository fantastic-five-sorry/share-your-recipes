package com.fantasticfour.shareyourrecipes.votes.dtos;

import java.util.Date;

import com.fantasticfour.shareyourrecipes.domains.ReplyComment;
import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class ReplyCommentDto {
    private String content;
    private Date createdAt;
    private String writerName;
    private String writerEmail;
    private String photoUrl;
    private Long writerId;

    public ReplyCommentDto() {
    }

    public ReplyCommentDto(ReplyComment reply) {

        User creator = reply.getCreator();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedDate();
        this.writerName = creator.getName();
        this.writerEmail = creator.getEmail();
        this.writerId = creator.getId();
        this.photoUrl = creator.getPhotoUrl();
    }


    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

    public Long getWriterId() {
        return this.writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }
}
