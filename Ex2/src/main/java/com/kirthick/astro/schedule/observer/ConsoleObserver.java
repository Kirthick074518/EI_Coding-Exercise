package com.kirthick.astro.schedule.observer;

public final class ConsoleObserver implements ScheduleObserver {
    @Override
    public void onEvent(ScheduleEvent event) {
        if (event == null) return;
        // Keep console notifications minimal; highlight conflicts explicitly
        if (event.type == ScheduleEventType.CONFLICT) {
            System.out.println("[Notice] " + event.message);
        }
    }
}
