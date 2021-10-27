package com.fantasticfour.shareyourrecipes.reports;

import com.fantasticfour.shareyourrecipes.reports.dtos.HandleReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.NewReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.ReportDto;
import com.fantasticfour.shareyourrecipes.reports.dtos.UpdateHandledReportDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportRestController {

    @Autowired
    ReportService reportService;

    @GetMapping("")
    public Page<ReportDto> getAllReports(Pageable page) {

        return reportService.getAllReports(page);

    }

    @PostMapping("/new")
    public ResponseEntity<?> postNewReport(@RequestBody NewReportDto dto) {

        try {
            reportService.createReport(dto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PostMapping("/handle")
    public ResponseEntity<?> handleReport(@RequestBody HandleReportDto dto) {

        try {
            reportService.handleReport(dto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/handle")
    public ResponseEntity<?> updateHandledReport(@RequestBody UpdateHandledReportDto dto) {

        try {
            reportService.updateHandledReport(dto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

}
