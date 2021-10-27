package com.fantasticfour.shareyourrecipes.reports.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateHandledReportDto {

    @NotNull
    private Long reportId;

    @NotBlank
    private String description;

    @NotBlank
    private String markStatus;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarkStatus() {
        return this.markStatus;
    }

    public void setMarkStatus(String markStatus) {
        this.markStatus = markStatus;
    }

    public UpdateHandledReportDto() {
    }

    public Long getReportId() {
        return this.reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

}
