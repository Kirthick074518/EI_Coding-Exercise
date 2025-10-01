package com.kirthick.dp.creational.factory;

import com.kirthick.dp.app.ConsoleIO;
import com.kirthick.dp.common.Validation;

public final class FactoryDemo {
    public static void run(ConsoleIO io) {
        io.println("-- Factory Pattern: Notifications --");
        String type = io.readLine("Type (email/sms/push): ");
        String to = io.readLine("To (email or phone or userId): ");
        String msg = io.readLine("Message: ");
        try {
            Notification n = NotificationFactory.create(type);
            n.send(Validation.requireNonBlank(to, "to"), Validation.requireNonBlank(msg, "message"));
            io.println("Sent using: " + n.getClass().getSimpleName());
        } catch (Exception e) {
            io.println("Failed: " + e.getMessage());
        }
    }
}
