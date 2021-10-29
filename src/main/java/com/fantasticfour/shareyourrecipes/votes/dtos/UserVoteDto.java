package com.fantasticfour.shareyourrecipes.votes.dtos;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class UserVoteDto {
    private String email;
    private String name;

    private String photoUrl;

    private Long userId;

    private Long subjectId;

    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public UserVoteDto() {
    }

    public UserVoteDto(User user, Long voteId, String type) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl() : "/imgs/default-avatar.png";
        this.name = user.getName();
        this.subjectId = voteId;
        this.type = type;
    }

}
