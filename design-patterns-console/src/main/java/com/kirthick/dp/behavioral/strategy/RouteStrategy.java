package com.kirthick.dp.behavioral.strategy;

public interface RouteStrategy {
    String name();
    Route plan(String start, String end);
}
