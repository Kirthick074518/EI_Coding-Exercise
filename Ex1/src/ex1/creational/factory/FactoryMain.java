package ex1.creational.factory;

public final class FactoryMain {
    public static void main(String[] args) {
        System.out.println("-- Factory Pattern --");
        Notification email = NotificationFactory.create("email");
        Notification sms = NotificationFactory.create("sms");
        Notification push = NotificationFactory.create("push");

        email.send("user@example.com", "Welcome aboard, astronaut!");
        sms.send("+911234567890", "Your OTP is 123456");
        push.send("astroUser", "New mission schedule available");
        System.out.println();
    }
}
