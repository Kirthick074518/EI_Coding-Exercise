package ex1.creational.factory;

public final class EmailNotification implements Notification {
    @Override
    public void send(String to, String message) {
        System.out.println("[Email] to " + to + ": " + message);
    }
    @Override
    public String name() { return "EmailNotification"; }
}
