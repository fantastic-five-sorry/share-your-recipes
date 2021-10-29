package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import com.fantasticfour.shareyourrecipes.domains.enums.QuestionStatus;

public class UpdateQuestionDTO {
    private Long id;
    private String title;
    private String content;
    private QuestionStatus status;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
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

    public QuestionStatus getStatus() {
        return this.status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }
    // private Long upVoteCount;
    // private Long downVoteCount;

    public UpdateQuestionDTO () {}
}
