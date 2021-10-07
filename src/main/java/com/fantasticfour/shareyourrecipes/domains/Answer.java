package com.fantasticfour.shareyourrecipes.domains;

import javax.persistence.*;

@Entity
@Table(name = "answers", schema = "public")
public class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;

    @ManyToOne
    private User answerer;

    private String content;

    @ManyToOne
    private Question question;

    private Long voteCount;

}
