package com.kirthick.dp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Application starting...");
        ConsoleIO io = new ConsoleIO();
        CommandRouter router = new CommandRouter(io);
        CommandLoop loop = new CommandLoop(io, router);
        try {
            loop.start();
        } catch (Exception ex) {
            log.error("Fatal error in main loop", ex);
        } finally {
            io.close();
            log.info("Application shutdown.");
        }
    }
}
