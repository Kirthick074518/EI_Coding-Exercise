package com.kirthick.dp.behavioral.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class EmailSubscriber implements Subscriber {
    private static final Logger log = LoggerFactory.getLogger(EmailSubscriber.class);
    private final String email;

    public EmailSubscriber(String email) {
        this.email = Objects.requireNonNull(email);
    }

    @Override
    public String id() { return "email:" + email; }

    @Override
    public void update(NewsCategory category, String headline) {
        log.info("[EMAIL -> {}] [{}] {}", email, category, headline);
    }
}
