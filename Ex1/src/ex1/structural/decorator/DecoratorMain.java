package ex1.structural.decorator;

public final class DecoratorMain {
    public static void main(String[] args) {
        System.out.println("-- Decorator Pattern --");
        UserRepository base = new InMemoryUserRepository();
        UserRepository repo = new LoggingUserRepository(new CacheUserRepository(base));

        User u = new User("u1", "Alice");
        repo.save(u);

        System.out.println("Fetching user twice to demonstrate cache hit on second call...");
        User a = repo.getById("u1");
        User b = repo.getById("u1");
        System.out.println("First fetch:  " + a);
        System.out.println("Second fetch: " + b);
        System.out.println();
    }
}
