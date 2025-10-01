package com.kirthick.dp.behavioral.observer;

import com.kirthick.dp.app.ConsoleIO;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public final class ObserverDemo {
    public static void run(ConsoleIO io) {
        io.println("-- Observer Pattern: News Publisher --");
        Publisher publisher = new NewsPublisher();

        String email = io.readLine("Enter email subscriber (or blank to skip): ");
        if (email != null && !email.isBlank()) {
            Subscriber e = new EmailSubscriber(email);
            subscribeFor(io, e, publisher);
        }
        String user = io.readLine("Enter mobile username (or blank to skip): ");
        if (user != null && !user.isBlank()) {
            Subscriber m = new MobileAppSubscriber(user);
            subscribeFor(io, m, publisher);
        }

        io.println("Type headlines to publish; type 'done' to finish.");
        Stream.generate(() -> io.readLine("Headline > "))
                .takeWhile(Objects::nonNull)
                .map(String::trim)
                .takeWhile(s -> !s.equalsIgnoreCase("done"))
                .forEach(headline -> {
                    String catStr = io.readLine("Category (sports/tech/finance/entertainment/weather): ");
                    NewsCategory category = parseCategory(catStr);
                    publisher.publish(category, headline);
                });
    }

    private static void subscribeFor(ConsoleIO io, Subscriber s, Publisher publisher) {
        String cats = io.readLine("Categories for " + s.id() + " (comma sep: sports,tech,finance,entertainment,weather): ");
        if (cats == null || cats.isBlank()) return;
        Arrays.stream(cats.split(","))
                .map(String::trim)
                .filter(str -> !str.isBlank())
                .map(ObserverDemo::parseCategory)
                .forEach(cat -> publisher.subscribe(cat, s));
    }

    private static NewsCategory parseCategory(String s) {
        String v = (s == null ? "" : s).trim().toUpperCase(Locale.ROOT);
        switch (v) {
            case "SPORTS": return NewsCategory.SPORTS;
            case "TECH": return NewsCategory.TECH;
            case "FINANCE": return NewsCategory.FINANCE;
            case "ENTERTAINMENT": return NewsCategory.ENTERTAINMENT;
            case "WEATHER": return NewsCategory.WEATHER;
            default: return NewsCategory.TECH;
        }
    }
}
