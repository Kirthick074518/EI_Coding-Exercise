package ex1.behavioral.strategy;

public final class DrivingStrategy implements RouteStrategy {
    @Override
    public Route plan(String start, String end) {
        // Simulate numbers
        return new Route(start, end, "driving", 12.3, 19);
    }
}
