package com.kirthick.astro.app;

import com.kirthick.astro.schedule.manager.ScheduleManager;
import com.kirthick.astro.schedule.observer.ConsoleObserver;
import com.kirthick.astro.schedule.observer.LoggingObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Astronaut Schedule Organizer starting...");
        ConsoleIO io = new ConsoleIO();
        CommandRouter router = new CommandRouter(io);
        CommandLoop loop = new CommandLoop(io, router);

        // Register observers on the singleton manager
        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new ConsoleObserver());
        manager.addObserver(new LoggingObserver());

        try {
            loop.start();
        } catch (Exception ex) {
            log.error("Fatal error in main loop", ex);
        } finally {
            io.close();
            log.info("Astronaut Schedule Organizer shutdown.");
        }
    }
}
