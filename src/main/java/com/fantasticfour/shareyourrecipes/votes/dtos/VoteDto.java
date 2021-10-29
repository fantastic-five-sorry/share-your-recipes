package com.fantasticfour.shareyourrecipes.votes.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VoteDto {

    private Long voterId;

    @NotNull
    private Long subjectId;

    @NotBlank
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VoteDto() {
    }

    public Long getVoterId() {
        return this.voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

}
