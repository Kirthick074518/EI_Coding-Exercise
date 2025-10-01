package ex1.behavioral.strategy;

public final class WalkingStrategy implements RouteStrategy {
    @Override
    public Route plan(String start, String end) {
        return new Route(start, end, "walking", 2.8, 36);
    }
}
