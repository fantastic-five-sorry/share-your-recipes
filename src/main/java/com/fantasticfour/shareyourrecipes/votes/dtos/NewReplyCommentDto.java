package com.fantasticfour.shareyourrecipes.votes.dtos;

public class NewReplyCommentDto {
    private Long writerId;
    private Long parentId;
    private String content;

    public Long getWriterId() {
        return this.writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parrentId) {
        this.parentId = parrentId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewReplyCommentDto() {
    }

}
