package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

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

    private String status;

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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HandledReport() {
    }

}
