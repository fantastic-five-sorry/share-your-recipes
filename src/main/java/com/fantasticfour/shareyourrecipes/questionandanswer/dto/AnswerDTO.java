package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.Question;

public class AnswerDTO {
    private UserInfo answerer;
    private String content;
    private Long voteCount;

    public UserInfo getAnswerer() {
        return this.answerer;
    }

    public void setAnswerer(UserInfo answerer) {
        this.answerer = answerer;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

  

    public Long getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    public AnswerDTO () {}

    public AnswerDTO(Answer answer) {
        this.answerer = new UserInfo(answer.getAnswerer());
        this.content = answer.getContent();
        this.voteCount = answer.getVoteCount();
    }
}
