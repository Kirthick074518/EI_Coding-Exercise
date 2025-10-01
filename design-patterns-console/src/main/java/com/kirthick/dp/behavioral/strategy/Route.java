package com.kirthick.dp.behavioral.strategy;

import java.util.Objects;

public final class Route {
    public final String start;
    public final String end;
    public final String mode;
    public final int minutes;
    public final double distanceKm;

    public Route(String start, String end, String mode, int minutes, double distanceKm) {
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
        this.mode = Objects.requireNonNull(mode);
        this.minutes = minutes;
        this.distanceKm = distanceKm;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s via %s: %.1f km ~ %d mins", start, end, mode, distanceKm, minutes);
    }
}
