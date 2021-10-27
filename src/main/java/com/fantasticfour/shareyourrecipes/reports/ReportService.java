package com.fantasticfour.shareyourrecipes.reports;

import com.fantasticfour.shareyourrecipes.reports.dtos.HandleReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.NewReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.ReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.UpdateHandledReportDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    Page<ReportDto> getAllReports(Pageable page);

    void createReport(NewReportDto dto);

    void handleReport(HandleReportDto dto);

    void updateHandledReport(UpdateHandledReportDto dto);
    
}
