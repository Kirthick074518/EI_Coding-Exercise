package com.kirthick.dp.structural.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class LoggingUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(LoggingUserRepository.class);
    private final UserRepository delegate;

    public LoggingUserRepository(UserRepository delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public User findById(String id) {
        long start = System.nanoTime();
        try {
            User u = delegate.findById(id);
            return u;
        } finally {
            long durMs = (System.nanoTime() - start) / 1_000_000;
            log.info("findById(id={}) took {} ms", id, durMs);
        }
    }

    @Override
    public void save(User user) {
        long start = System.nanoTime();
        try {
            delegate.save(user);
        } finally {
            long durMs = (System.nanoTime() - start) / 1_000_000;
            log.info("save(id={}) took {} ms", user.id, durMs);
        }
    }
}
