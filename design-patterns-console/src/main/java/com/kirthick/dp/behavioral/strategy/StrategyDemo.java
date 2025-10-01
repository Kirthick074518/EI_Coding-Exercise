package com.kirthick.dp.behavioral.strategy;

import com.kirthick.dp.app.ConsoleIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StrategyDemo {
    private static final Logger log = LoggerFactory.getLogger(StrategyDemo.class);

    public static void run(ConsoleIO io) {
        io.println("-- Strategy Pattern: Route Planner --");
        var planner = new RoutePlanner(new DrivingStrategy(), new WalkingStrategy(), new TransitStrategy());
        String start = io.readLine("Enter start: ");
        String end = io.readLine("Enter end: ");
        String mode = io.readLine("Mode (driving/walking/transit): ");
        try {
            planner.use(mode);
            Route route = planner.plan(start, end);
            io.println("Planned: " + route);
            log.info("Route planned: {}", route);
        } catch (Exception e) {
            io.println("Failed to plan route: " + e.getMessage());
            log.warn("Planning failed", e);
        }
    }
}
