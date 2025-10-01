package ex1.creational.factory;

public interface Notification {
    void send(String to, String message);
    String name();
}
