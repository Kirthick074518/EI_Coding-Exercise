package com.kirthick.astro.schedule.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggingObserver implements ScheduleObserver {
    private static final Logger log = LoggerFactory.getLogger(LoggingObserver.class);

    @Override
    public void onEvent(ScheduleEvent event) {
        if (event == null) return;
        switch (event.type) {
            case ADDED:
            case UPDATED:
            case COMPLETED:
            case REMOVED:
                log.info("{}: {}", event.type, event.task);
                break;
            case CONFLICT:
                log.warn("CONFLICT: {} with existing {}", event.task, event.conflictWith);
                break;
            default:
                log.info("Event: {}", event.type);
        }
    }
}
