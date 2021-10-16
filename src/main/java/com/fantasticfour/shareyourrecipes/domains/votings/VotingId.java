package com.fantasticfour.shareyourrecipes.domains.votings;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class VotingId implements Serializable {

    private Long voterId;

    private Long subjectId;

    public Long getVoterId() {
        return this.voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public VotingId(Long voterId, Long subjectId) {
        this.voterId = voterId;
        this.subjectId = subjectId;
    }

    public VotingId() {
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

}
