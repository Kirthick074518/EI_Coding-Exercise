package com.kirthick.dp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 import com.kirthick.dp.behavioral.observer.ObserverDemo;
 import com.kirthick.dp.behavioral.strategy.StrategyDemo;
 import com.kirthick.dp.creational.builder.BuilderDemo;
 import com.kirthick.dp.creational.factory.FactoryDemo;
 import com.kirthick.dp.structural.adapter.AdapterDemo;
 import com.kirthick.dp.structural.decorator.DecoratorDemo;
 
 import java.util.*;

public final class CommandRouter {
    private static final Logger log = LoggerFactory.getLogger(CommandRouter.class);
    private final Map<String, CommandHandler> handlers = new LinkedHashMap<>();
    private final ConsoleIO io;

    public CommandRouter(ConsoleIO io) {
        this.io = io;
        register("help", this::help, "Show available commands");
        register("clear", this::clear, "Clear the console");
        register("strategy", args -> StrategyDemo.run(io), "Run Strategy pattern demo");
        register("observer", args -> ObserverDemo.run(io), "Run Observer pattern demo");
        register("factory", args -> FactoryDemo.run(io), "Run Factory pattern demo");
        register("builder", args -> BuilderDemo.run(io), "Run Builder pattern demo");
        register("adapter", args -> AdapterDemo.run(io), "Run Adapter pattern demo");
        register("decorator", args -> DecoratorDemo.run(io), "Run Decorator pattern demo");
        register("exit", args -> io.println("Goodbye!"), "Exit the application");
    }

    public interface CommandHandler {
        void handle(String[] args);
    }

    private static final class CommandMeta {
        final String name;
        final String description;
        final CommandHandler handler;
        CommandMeta(String name, String description, CommandHandler handler) {
            this.name = name; this.description = description; this.handler = handler;
        }
    }

    private final List<CommandMeta> metaList = new ArrayList<>();

    public void register(String name, CommandHandler handler, String description) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(handler);
        handlers.put(name.toLowerCase(Locale.ROOT), handler);
        metaList.add(new CommandMeta(name.toLowerCase(Locale.ROOT), description, handler));
    }

    public boolean isExitCommand(String line) {
        return line != null && line.equalsIgnoreCase("exit");
    }

    public void route(String line) {
        if (line == null || line.isBlank()) {
            return;
        }
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        CommandHandler handler = handlers.get(command);
        if (handler == null) {
            io.println("Unknown command: " + command + " (type 'help' to see commands)");
            return;
        }
        try {
            handler.handle(args);
        } catch (Exception ex) {
            log.error("Error while executing command '{}'", command, ex);
            io.println("Command failed: " + ex.getMessage());
        }
    }

    private void help(String[] args) {
        io.println("Available commands:");
        for (CommandMeta meta : metaList) {
            io.println("  - " + String.format("%-10s", meta.name) + " : " + meta.description);
        }
    }

    private void clear(String[] args) {
        try {
            if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) {
                io.println("\n".repeat(50));
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            io.println("\n".repeat(50));
        }
    }
}
