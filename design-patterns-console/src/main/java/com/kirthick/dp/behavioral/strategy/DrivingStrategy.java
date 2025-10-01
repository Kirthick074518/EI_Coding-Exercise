package com.kirthick.dp.behavioral.strategy;

import java.util.concurrent.ThreadLocalRandom;

public final class DrivingStrategy implements RouteStrategy {
    @Override public String name() { return "driving"; }

    @Override
    public Route plan(String start, String end) {
        double distance = ThreadLocalRandom.current().nextDouble(5, 30); // km
        int minutes = (int) Math.round(distance / 40.0 * 60); // avg 40 km/h with traffic
        return new Route(start, end, name(), minutes, distance);
    }
}
