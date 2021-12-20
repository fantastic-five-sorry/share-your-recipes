package com.fantasticfour.shareyourrecipes.configs;

import com.fantasticfour.shareyourrecipes.recipes.services.IndexingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private IndexingService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            service.initiateIndexing();
            // log.info("initiated indexing successfully");
        } catch (InterruptedException e) {
            // log.error("Failed to reindex entities ", e);
        }
    }
}
