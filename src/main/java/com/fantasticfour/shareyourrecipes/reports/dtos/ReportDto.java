package com.fantasticfour.shareyourrecipes.reports.dtos;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.reports.Report;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;

public class ReportDto {

    private Long id;
    private String reason;
    private RecipeDTO recipe;
    private UserInfo reporter;

    private Boolean isHandled;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.reason = report.getReason();
        this.recipe = new RecipeDTO(report.getRecipe());
        this.reporter = new UserInfo(report.getReporter());
        isHandled = report.isHandled();
    }


    public Boolean isIsHandled() {
        return this.isHandled;
    }

    public Boolean getIsHandled() {
        return this.isHandled;
    }

    public void setIsHandled(Boolean isHandled) {
        this.isHandled = isHandled;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RecipeDTO getRecipe() {
        return this.recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    public UserInfo getReporter() {
        return this.reporter;
    }

    public void setReporter(UserInfo reporter) {
        this.reporter = reporter;
    }

    public ReportDto() {
    }

}
