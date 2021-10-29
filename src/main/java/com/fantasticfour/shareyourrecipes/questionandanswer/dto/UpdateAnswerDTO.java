package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

public class UpdateAnswerDTO {
    private Long id;

    
    private String content;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    // private Long upVoteCount;
    // private Long downVoteCount;
    public UpdateAnswerDTO() {}
}
