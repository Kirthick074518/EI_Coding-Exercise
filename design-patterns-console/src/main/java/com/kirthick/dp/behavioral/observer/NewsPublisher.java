package com.kirthick.dp.behavioral.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class NewsPublisher implements Publisher {
    private static final Logger log = LoggerFactory.getLogger(NewsPublisher.class);
    private final Map<NewsCategory, Set<Subscriber>> subs = new EnumMap<>(NewsCategory.class);

    public NewsPublisher() {
        for (NewsCategory c : NewsCategory.values()) {
            subs.put(c, new LinkedHashSet<>());
        }
    }

    @Override
    public void subscribe(NewsCategory category, Subscriber subscriber) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(subscriber);
        subs.get(category).add(subscriber);
        log.info("Subscribed {} to {}", subscriber.id(), category);
    }

    @Override
    public void unsubscribe(NewsCategory category, Subscriber subscriber) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(subscriber);
        subs.get(category).remove(subscriber);
        log.info("Unsubscribed {} from {}", subscriber.id(), category);
    }

    @Override
    public void publish(NewsCategory category, String headline) {
        Objects.requireNonNull(category);
        String hl = Objects.requireNonNull(headline);
        log.info("Publishing [{}] {}", category, hl);
        for (Subscriber s : subs.get(category)) {
            try {
                s.update(category, hl);
            } catch (Exception e) {
                log.warn("Subscriber {} failed to receive update: {}", s.id(), e.toString());
            }
        }
    }
}
