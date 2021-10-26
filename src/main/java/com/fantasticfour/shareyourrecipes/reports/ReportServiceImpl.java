package com.fantasticfour.shareyourrecipes.reports;

import java.time.LocalDateTime;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ReportStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.reports.HandledReport;
import com.fantasticfour.shareyourrecipes.domains.reports.Report;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.reports.dtos.HandleReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.NewReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.ReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.UpdateHandledReportDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepo reportRepo;
    @Autowired
    HandledReportRepo handledReportRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RecipeRepository recipeRepo;

    @Override
    public Page<ReportDto> getAllReports(Pageable page) {
        return reportRepo.findAll(page).map(ReportDto::new);
    }

    @Override
    public void createReport(NewReportDto dto) {

        Report report = new Report();
        User reporter = userRepo.findValidUserById(dto.getReporterId());
        Recipe recipe = recipeRepo.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalStateException("not found this"));
        report.setCreatedAt(LocalDateTime.now());
        report.setReason(dto.getReason());
        report.setRecipe(recipe);
        report.setReporter(reporter);

        reportRepo.save(report);
    }

    @Override
    public void handleReport(HandleReportDto dto) {
        HandledReport report = new HandledReport();
        User handler = userRepo.findValidUserById(dto.getHandlerId());
        report.setId(dto.getReportId());
        report.setDescription(dto.getDescription());
        report.setStatus(ReportStatus.valueOf(dto.getMarkStatus()));
        report.setHandler(handler);

        handledReportRepo.save(report);

    }

    @Override
    public void updateHandledReport(UpdateHandledReportDto dto) {

        HandledReport report = handledReportRepo.findById(dto.getReportId())
                .orElseThrow(() -> new IllegalStateException("report not found"));
        report.setDescription(dto.getDescription());
        report.setStatus(ReportStatus.valueOf(dto.getMarkStatus()));

        handledReportRepo.save(report);
    }
}
