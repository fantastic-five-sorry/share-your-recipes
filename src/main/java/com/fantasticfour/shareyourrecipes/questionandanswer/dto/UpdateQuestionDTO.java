package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

public class UpdateQuestionDTO {
    private String title;
    private String content;
    private String status;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // private Long upVoteCount;
    // private Long downVoteCount;

    public UpdateQuestionDTO () {}
}
