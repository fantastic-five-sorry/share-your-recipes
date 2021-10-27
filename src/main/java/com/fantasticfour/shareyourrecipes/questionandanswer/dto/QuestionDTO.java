package com.fantasticfour.shareyourrecipes.questionandanswer.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.Question;
import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class QuestionDTO {
    private String slug;
    private String title;
    private String content;
    private String status;
    private UserInfo creator;
    private List<AnswerDTO> answerDTOs = new ArrayList<>();
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

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

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

    public UserInfo getCreator() {
        return this.creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }

    public List<AnswerDTO> getAnswerDTOs() {
        return this.answerDTOs;
    }

    public void setAnswerDTOs(List<AnswerDTO> answerDTOs) {
        this.answerDTOs = answerDTOs;
    }

    public QuestionDTO() {
    }

    public QuestionDTO(Question question) {
        this.slug = question.getSlug();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.status = question.getStatus().toString();
        this.creator = new UserInfo(question.getCreator());
        this.answerDTOs = question.getAnswers().stream().map(AnswerDTO::new).collect(Collectors.toList());
        this.downVoteCount = question.getDownVoteCount();
        this.upVoteCount = question.getUpVoteCount();
    }

}
