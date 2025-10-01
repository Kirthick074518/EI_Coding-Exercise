package com.kirthick.dp.structural.decorator;

import com.kirthick.dp.app.ConsoleIO;
import com.kirthick.dp.common.Validation;

public final class DecoratorDemo {
    public static void run(ConsoleIO io) {
        io.println("-- Decorator Pattern: Repository with Caching & Logging --");
        UserRepository repo = new LoggingUserRepository(new CacheUserRepository(new InMemoryUserRepository()));

        String id = Validation.requireNonBlank(io.readLine("Enter user id: "), "id");
        String name = Validation.requireNonBlank(io.readLine("Enter name: "), "name");

        repo.save(new User(id, name));

        io.println("Fetching user twice to demonstrate cache hit on second call...");
        long t1 = System.nanoTime();
        User u1 = repo.findById(id);
        long t2 = System.nanoTime();
        User u2 = repo.findById(id);
        long t3 = System.nanoTime();

        io.println("First fetch:  " + u1 + " in " + ((t2 - t1) / 1_000_000) + " ms");
        io.println("Second fetch: " + u2 + " in " + ((t3 - t2) / 1_000_000) + " ms");
        io.println("(See logs for detailed timings)");
    }
}
