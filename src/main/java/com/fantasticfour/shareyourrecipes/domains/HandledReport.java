package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "handled_reports", schema = "public")
public class HandledReport {

    @Id
    @Column(name = "report_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne
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
