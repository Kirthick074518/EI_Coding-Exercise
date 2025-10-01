package com.kirthick.dp.structural.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User findById(String id) {
        Objects.requireNonNull(id);
        simulateLatency(120);
        return store.get(id);
    }

    @Override
    public void save(User user) {
        Objects.requireNonNull(user);
        simulateLatency(80);
        store.put(user.id, user);
        log.info("Saved {}", user);
    }

    private static void simulateLatency(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }
}
