package com.kirthick.dp.creational.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class SmsNotification implements Notification {
    private static final Logger log = LoggerFactory.getLogger(SmsNotification.class);

    @Override
    public String type() { return "sms"; }

    @Override
    public void send(String to, String message) {
        String t = Objects.requireNonNull(to);
        String m = Objects.requireNonNull(message);
        log.info("[SMS] to={} msg={}", t, m);
    }
}
