package com.kirthick.dp.structural.decorator;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheUserRepository implements UserRepository {
    private final UserRepository delegate;
    private final Map<String, User> cache = new ConcurrentHashMap<>();

    public CacheUserRepository(UserRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public User findById(String id) {
        return cache.computeIfAbsent(id, delegate::findById);
    }

    @Override
    public void save(User user) {
        delegate.save(user);
        cache.put(user.id, user);
    }
}
