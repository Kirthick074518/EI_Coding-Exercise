package com.kirthick.dp.creational.factory;

public interface Notification {
    String type();
    void send(String to, String message);
}
