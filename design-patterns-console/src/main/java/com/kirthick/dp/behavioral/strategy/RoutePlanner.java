package com.kirthick.dp.behavioral.strategy;

import com.kirthick.dp.common.Validation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class RoutePlanner {
    private final Map<String, RouteStrategy> strategies = new HashMap<>();
    private RouteStrategy active;

    public RoutePlanner(RouteStrategy... strategies) {
        for (RouteStrategy s : strategies) {
            this.strategies.put(s.name().toLowerCase(Locale.ROOT), s);
        }
        this.active = strategies.length > 0 ? strategies[0] : null;
    }

    public void use(String name) {
        String key = Validation.requireNonBlank(name, "strategy name").toLowerCase(Locale.ROOT);
        RouteStrategy s = strategies.get(key);
        Validation.require(s != null, "Unknown strategy: " + name);
        this.active = s;
    }

    public Route plan(String start, String end) {
        Validation.require(active != null, "No active strategy selected");
        return active.plan(Validation.requireNonBlank(start, "start"),
                Validation.requireNonBlank(end, "end"));
    }
}
