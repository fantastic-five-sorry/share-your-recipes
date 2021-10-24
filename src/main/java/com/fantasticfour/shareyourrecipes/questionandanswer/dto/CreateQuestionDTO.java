package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import java.util.ArrayList;
import java.util.List;



public class CreateQuestionDTO {
    private String title;
    private String content;
    private String status;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    public CreateQuestionDTO() {}
}
