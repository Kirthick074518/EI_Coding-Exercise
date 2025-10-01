package com.kirthick.dp.behavioral.strategy;

import java.util.concurrent.ThreadLocalRandom;

public final class TransitStrategy implements RouteStrategy {
    @Override public String name() { return "transit"; }

    @Override
    public Route plan(String start, String end) {
        double distance = ThreadLocalRandom.current().nextDouble(5, 25); // km
        int minutes = (int) Math.round(distance / 25.0 * 60 + 5); // avg 25 km/h + wait time
        return new Route(start, end, name(), minutes, distance);
    }
}
