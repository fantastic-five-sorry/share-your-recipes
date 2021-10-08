package com.fantasticfour.shareyourrecipes.domains;

import java.util.Date;

import com.fantasticfour.shareyourrecipes.domains.enums.EVotingFor;

public class Voting {

    private EVotingFor votingFor;

    private User voter;

    private Date createdAt;

    private Long id;
}
