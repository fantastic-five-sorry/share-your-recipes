package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class CreateQuestionDTO {
    @NotBlank
    @Length(max = 100)
    private String title;
    @NotBlank
    @Length(max = 65535)
    private String content;
    @NotBlank
    private Long creatorId;

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

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public CreateQuestionDTO() {
    }
}
