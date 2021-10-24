package com.fantasticfour.shareyourrecipes.domains.reports;

import javax.persistence.*;

import com.fantasticfour.shareyourrecipes.domains.AuditModel;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ReportStatus;

@Entity
@Table(name = "handled_reports", schema = "public")
public class HandledReport extends AuditModel {

    @Id
    @Column(name = "report_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    private User handler;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ReportStatus status;

    public Report getReport() {
        return this.report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public User getHandler() {
        return this.handler;
    }

    public void setHandler(User handler) {
        this.handler = handler;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public HandledReport() {
        
    }

}
