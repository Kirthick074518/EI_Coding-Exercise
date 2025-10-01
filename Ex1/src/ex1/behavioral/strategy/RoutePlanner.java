package ex1.behavioral.strategy;

public final class RoutePlanner {
    private RouteStrategy strategy;
    public RoutePlanner(RouteStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(RouteStrategy s) { this.strategy = s; }
    public Route plan(String start, String end) { return strategy.plan(start, end); }
}
