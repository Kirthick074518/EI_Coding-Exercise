package com.kirthick.astro.app;

import java.util.Objects;
import java.util.stream.Stream;

public final class CommandLoop {
    private final ConsoleIO io;
    private final CommandRouter router;

    public CommandLoop(ConsoleIO io, CommandRouter router) {
        this.io = io;
        this.router = router;
    }

    public void start() {
        printBanner();
        router.route("help");
        Stream.generate(() -> io.readLine("astro> "))
                .takeWhile(Objects::nonNull)
                .takeWhile(line -> !router.isExitCommand(line))
                .forEach(router::route);
    }

    private void printBanner() {
        io.println("===============================================");
        io.println("   Astronaut Daily Schedule Organizer");
        io.println("   Type 'help' to see available commands.");
        io.println("   Type 'exit' to quit.");
        io.println("===============================================");
    }
}
