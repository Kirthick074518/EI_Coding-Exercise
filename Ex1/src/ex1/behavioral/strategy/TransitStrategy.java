package ex1.behavioral.strategy;

public final class TransitStrategy implements RouteStrategy {
    @Override
    public Route plan(String start, String end) {
        return new Route(start, end, "transit", 10.1, 24);
    }
}
