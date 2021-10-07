package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

@Entity
@Table(name="reports", schema = "public")
public class Report extends AuditModel{

    @Id
    @GeneratedValue(generator = "report_generator")
    @SequenceGenerator(name = "report_generator", sequenceName = "report_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    private String reason;

    // private User handler;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private Recipe recipe;

}

// handle : report_id + name + status;
