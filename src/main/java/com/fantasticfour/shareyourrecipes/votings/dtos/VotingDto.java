package com.fantasticfour.shareyourrecipes.votings.dtos;

import javax.validation.constraints.NotBlank;

public class VotingDto {
    
    private Long voterId;

    @NotBlank
    private Long subjectVotingToId;

    @NotBlank
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VotingDto() {
    }

    public Long getVoterId() {
        return this.voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Long getSubjectVotingToId() {
        return this.subjectVotingToId;
    }

    public void setSubjectVotingToId(Long subjectVotingToId) {
        this.subjectVotingToId = subjectVotingToId;
    }

}
