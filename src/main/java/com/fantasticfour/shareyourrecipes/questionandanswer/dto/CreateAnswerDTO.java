package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class CreateAnswerDTO {

    private Long answererId;
    @NotBlank
    @Length(max = 65535)
    private String content;
    @NotBlank
    private Long questionId;

    public Long getAnswererId() {
        return this.answererId;
    }

    public void setAnswererId(Long answererId) {
        this.answererId = answererId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public CreateAnswerDTO() {
    }

}
