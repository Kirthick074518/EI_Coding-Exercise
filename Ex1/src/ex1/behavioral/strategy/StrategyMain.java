package ex1.behavioral.strategy;

public final class StrategyMain {
    public static void main(String[] args) {
        System.out.println("-- Strategy Pattern --");
        RoutePlanner planner = new RoutePlanner(new DrivingStrategy());
        String start = "Home"; String end = "Office";
        System.out.println(planner.plan(start, end));
        planner.setStrategy(new WalkingStrategy());
        System.out.println(planner.plan(start, end));
        planner.setStrategy(new TransitStrategy());
        System.out.println(planner.plan(start, end));
        System.out.println();
    }
}
