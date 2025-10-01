package com.kirthick.dp.creational.factory;

import com.kirthick.dp.common.Validation;

public final class NotificationFactory {
    private NotificationFactory() {}

    public static Notification create(String type) {
        String t = Validation.requireNonBlank(type, "notification type").trim().toLowerCase();
        switch (t) {
            case "email": return new EmailNotification();
            case "sms": return new SmsNotification();
            case "push": return new PushNotification();
            default: throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}
