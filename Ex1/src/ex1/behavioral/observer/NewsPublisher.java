package ex1.behavioral.observer;

import java.util.*;

public final class NewsPublisher implements Publisher {
    private final Map<NewsCategory, List<Subscriber>> subs = new EnumMap<>(NewsCategory.class);

    public NewsPublisher() {
        for (NewsCategory c : NewsCategory.values()) subs.put(c, new ArrayList<>());
    }

    @Override
    public void subscribe(NewsCategory category, Subscriber subscriber) {
        if (category == null || subscriber == null) return;
        subs.get(category).add(subscriber);
    }

    @Override
    public void unsubscribe(NewsCategory category, Subscriber subscriber) {
        if (category == null || subscriber == null) return;
        subs.get(category).remove(subscriber);
    }

    @Override
    public void publish(NewsCategory category, String headline) {
        System.out.println("Publish [" + category + "]: " + headline);
        for (Subscriber s : subs.getOrDefault(category, List.of())) {
            s.onNews(category, headline);
        }
    }
}
