package com.kirthick.astro.schedule.observer;

import com.kirthick.astro.schedule.domain.Task;

public final class ScheduleEvent {
    public final ScheduleEventType type;
    public final String message;
    public final Task task;
    public final Task conflictWith;

    public ScheduleEvent(ScheduleEventType type, String message, Task task, Task conflictWith) {
        this.type = type;
        this.message = message;
        this.task = task;
        this.conflictWith = conflictWith;
    }

    public static ScheduleEvent added(Task t) {
        return new ScheduleEvent(ScheduleEventType.ADDED, "Task added", t, null);
    }

    public static ScheduleEvent removed(Task t) {
        return new ScheduleEvent(ScheduleEventType.REMOVED, "Task removed", t, null);
    }

    public static ScheduleEvent updated(Task t) {
        return new ScheduleEvent(ScheduleEventType.UPDATED, "Task updated", t, null);
    }

    public static ScheduleEvent completed(Task t) {
        return new ScheduleEvent(ScheduleEventType.COMPLETED, "Task completed", t, null);
    }

    public static ScheduleEvent conflict(Task newTask, Task existing) {
        String msg = "Task conflicts with existing task \"" + (existing == null ? "" : existing.description()) + "\"";
        return new ScheduleEvent(ScheduleEventType.CONFLICT, msg, newTask, existing);
    }
}
