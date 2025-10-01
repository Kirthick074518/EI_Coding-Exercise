package com.kirthick.dp.behavioral.strategy;

import java.util.concurrent.ThreadLocalRandom;

public final class WalkingStrategy implements RouteStrategy {
    @Override public String name() { return "walking"; }

    @Override
    public Route plan(String start, String end) {
        double distance = ThreadLocalRandom.current().nextDouble(0.5, 5); // km
        int minutes = (int) Math.round(distance / 4.5 * 60); // avg 4.5 km/h
        return new Route(start, end, name(), minutes, distance);
    }
}
