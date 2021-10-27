package com.fantasticfour.shareyourrecipes.reports.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HandleReportDto {
    @NotNull
    private Long reportId;

    private Long handlerId;

    @NotBlank
    private String description;

    @NotBlank
    private String markStatus;

    public Long getReportId() {
        return this.reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getHandlerId() {
        return this.handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

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

    public HandleReportDto() {
    }

}
