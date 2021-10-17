package com.fantasticfour.shareyourrecipes.votings.dtos;

import javax.validation.constraints.NotBlank;

public class VotingDto {
    @NotBlank
    private Long voterId;

    @NotBlank
    private Long subjectVotingToId;

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
