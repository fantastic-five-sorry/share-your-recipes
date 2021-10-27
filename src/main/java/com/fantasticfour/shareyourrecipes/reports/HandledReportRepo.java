package com.fantasticfour.shareyourrecipes.reports;

import com.fantasticfour.shareyourrecipes.domains.reports.HandledReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandledReportRepo extends JpaRepository<HandledReport, Long> {

}
