package ex1.creational.factory;

public final class SmsNotification implements Notification {
    @Override
    public void send(String to, String message) {
        System.out.println("[SMS] to " + to + ": " + message);
    }
    @Override
    public String name() { return "SmsNotification"; }
}
