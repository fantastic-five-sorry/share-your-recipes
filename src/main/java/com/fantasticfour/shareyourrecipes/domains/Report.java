package com.fantasticfour.shareyourrecipes.domains;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "reports", schema = "public")
public class Report {

    @Id
    @GeneratedValue(generator = "report_generator")
    @SequenceGenerator(name = "report_generator", sequenceName = "report_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String reason;

    // private User handler;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    private Date createdAt;
}

// handle : report_id + name + status;
