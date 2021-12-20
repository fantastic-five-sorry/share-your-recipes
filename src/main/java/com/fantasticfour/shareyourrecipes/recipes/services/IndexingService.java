package com.fantasticfour.shareyourrecipes.recipes.services;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexingService {

    Logger logger = LoggerFactory.getLogger(IndexingService.class);
    @Autowired
    private EntityManager em;

    @Transactional
    public void initiateIndexing() throws InterruptedException {
        logger.info("Initiating indexing...");
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
        logger.info("All entities indexed");
    }
}
