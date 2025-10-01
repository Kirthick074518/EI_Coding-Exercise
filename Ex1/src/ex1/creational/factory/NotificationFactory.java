package ex1.creational.factory;

public final class NotificationFactory {
    private NotificationFactory() {}

    public static Notification create(String type) {
        if (type == null) return new EmailNotification();
        switch (type.trim().toLowerCase()) {
            case "email": return new EmailNotification();
            case "sms": return new SmsNotification();
            case "push": return new PushNotification();
            default: return new EmailNotification();
        }
    }
}
