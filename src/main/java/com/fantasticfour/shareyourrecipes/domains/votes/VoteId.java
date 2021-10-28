package com.fantasticfour.shareyourrecipes.domains.votes;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class VoteId implements Serializable {

    private Long voterId;

    private Long subjectId;

    public Long getVoterId() {
        return this.voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public VoteId(Long voterId, Long subjectId) {
        this.voterId = voterId;
        this.subjectId = subjectId;
    }

    public VoteId() {
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

}
