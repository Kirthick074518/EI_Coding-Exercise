package ex1.creational.factory;

public final class PushNotification implements Notification {
    @Override
    public void send(String to, String message) {
        System.out.println("[Push] to user " + to + ": " + message);
    }
    @Override
    public String name() { return "PushNotification"; }
}
