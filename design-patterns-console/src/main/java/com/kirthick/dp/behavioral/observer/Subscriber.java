package com.kirthick.dp.behavioral.observer;

public interface Subscriber {
    String id();
    void update(NewsCategory category, String headline);
}
