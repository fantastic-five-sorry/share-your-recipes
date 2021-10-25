package com.fantasticfour.shareyourrecipes.reports;

import com.fantasticfour.shareyourrecipes.domains.reports.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {

}
