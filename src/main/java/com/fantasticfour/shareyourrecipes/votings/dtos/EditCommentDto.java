package com.fantasticfour.shareyourrecipes.votings.dtos;

import javax.validation.constraints.NotBlank;

public class EditCommentDto {
    @NotBlank
    private Long id;

    @NotBlank
    private String newContent;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewContent() {
        return this.newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public EditCommentDto() {
    }

}
