package com.fantasticfour.shareyourrecipes.reports;

import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    ReportRepo reportRepo;
    HandledReportRepo handledReportRepo;
}
