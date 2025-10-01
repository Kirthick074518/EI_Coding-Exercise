package com.kirthick.astro.schedule.domain;

import com.kirthick.astro.common.Validation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Task {
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    private final String description;
    private final LocalTime start;
    private final LocalTime end;
    private final Priority priority;
    private final boolean completed;

    public Task(String description, LocalTime start, LocalTime end, Priority priority, boolean completed) {
        this.description = Validation.requireNonBlank(description, "description");
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
        this.priority = Objects.requireNonNull(priority);
        Validation.require(start.isBefore(end), "start time must be before end time");
        this.completed = completed;
    }

    public String description() { return description; }
    public LocalTime start() { return start; }
    public LocalTime end() { return end; }
    public Priority priority() { return priority; }
    public boolean isCompleted() { return completed; }

    public Task withCompleted(boolean newCompleted) {
        return new Task(description, start, end, priority, newCompleted);
    }

    public Task with(String newDescription, LocalTime newStart, LocalTime newEnd, Priority newPriority) {
        return new Task(newDescription, newStart, newEnd, newPriority, completed);
    }

    public String timeWindow() {
        return start.format(TF) + " - " + end.format(TF);
    }

    @Override
    public String toString() {
        return timeWindow() + ": " + description + " [" + priority + (completed ? ", Completed" : "") + "]";
    }
}
