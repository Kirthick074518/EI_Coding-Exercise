package ex1.behavioral.strategy;

public final class Route {
    public final String start;
    public final String end;
    public final String mode;
    public final double distanceKm;
    public final int durationMin;

    public Route(String start, String end, String mode, double distanceKm, int durationMin) {
        this.start = start;
        this.end = end;
        this.mode = mode;
        this.distanceKm = distanceKm;
        this.durationMin = durationMin;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s via %s: %.1f km ~ %d mins", start, end, mode, distanceKm, durationMin);
    }
}
