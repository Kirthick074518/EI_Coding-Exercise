package com.kirthick.dp.behavioral.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class MobileAppSubscriber implements Subscriber {
    private static final Logger log = LoggerFactory.getLogger(MobileAppSubscriber.class);
    private final String username;

    public MobileAppSubscriber(String username) {
        this.username = Objects.requireNonNull(username);
    }

    @Override
    public String id() { return "mobile:" + username; }

    @Override
    public void update(NewsCategory category, String headline) {
        log.info("[MOBILE -> {}] [{}] {}", username, category, headline);
    }
}
