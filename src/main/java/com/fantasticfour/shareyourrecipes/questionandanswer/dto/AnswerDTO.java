package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Answer;
import com.fantasticfour.shareyourrecipes.domains.Question;

public class AnswerDTO {
    private UserInfo answerer;
    private String content;
    private Long upVoteCount;
    private Long downVoteCount;

    public Long getUpVoteCount() {
        return this.upVoteCount;
    }

    public void setUpVoteCount(Long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public Long getDownVoteCount() {
        return this.downVoteCount;
    }

    public void setDownVoteCount(Long downVoteCount) {
        this.downVoteCount = downVoteCount;
    }


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



    public AnswerDTO () {}

    public AnswerDTO(Answer answer) {
        this.answerer = new UserInfo(answer.getAnswerer());
        this.content = answer.getContent();
        this.upVoteCount = answer.getUpVoteCount();
        this.downVoteCount = answer.getDownVoteCount();

    }
}
