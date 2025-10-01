package com.kirthick.dp.behavioral.observer;

public interface Publisher {
    void subscribe(NewsCategory category, Subscriber subscriber);
    void unsubscribe(NewsCategory category, Subscriber subscriber);
    void publish(NewsCategory category, String headline);
}
