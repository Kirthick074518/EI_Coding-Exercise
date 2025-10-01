package ex1.behavioral.observer;

public final class ObserverMain {
    public static void main(String[] args) {
        System.out.println("-- Observer Pattern --");
        NewsPublisher publisher = new NewsPublisher();
        EmailSubscriber email = new EmailSubscriber("astro@example.com");
        MobileAppSubscriber mobile = new MobileAppSubscriber("astro");
        publisher.subscribe(NewsCategory.TECH, email);
        publisher.subscribe(NewsCategory.SPORTS, mobile);
        publisher.subscribe(NewsCategory.FINANCE, email);

        publisher.publish(NewsCategory.TECH, "New space-capable CPU announced");
        publisher.publish(NewsCategory.SPORTS, "Zero-G football league formed");
        publisher.publish(NewsCategory.WEATHER, "Solar winds calm today");
        System.out.println();
    }
}
