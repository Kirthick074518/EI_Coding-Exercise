package com.kirthick.dp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.stream.Stream;

public final class CommandLoop {
    private static final Logger log = LoggerFactory.getLogger(CommandLoop.class);
    private final ConsoleIO io;
    private final CommandRouter router;

    public CommandLoop(ConsoleIO io, CommandRouter router) {
        this.io = io;
        this.router = router;
    }

    public void start() {
        printBanner();
        router.route("help");
        Stream.generate(() -> io.readLine("dp> "))
                .takeWhile(Objects::nonNull)
                .takeWhile(line -> !router.isExitCommand(line))
                .forEach(router::route);
    }

    private void printBanner() {
        io.println("===============================================");
        io.println("   Design Patterns Console - by Kirthick");
        io.println("   Type 'help' to see available commands.");
        io.println("   Type 'exit' to quit.");
        io.println("===============================================");
    }
}
