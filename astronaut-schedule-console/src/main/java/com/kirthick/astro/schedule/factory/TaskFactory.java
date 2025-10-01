package com.kirthick.astro.schedule.factory;

import com.kirthick.astro.common.Validation;
import com.kirthick.astro.common.exceptions.ValidationException;
import com.kirthick.astro.schedule.domain.Priority;
import com.kirthick.astro.schedule.domain.Task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class TaskFactory {
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    private TaskFactory() {}

    public static Task create(String description, String start, String end, String priority) {
        String desc = Validation.requireNonBlank(description, "description");
        LocalTime st = parseTime(start, "start time");
        LocalTime et = parseTime(end, "end time");
        Priority pr = Priority.from(priority);
        return new Task(desc, st, et, pr, false);
    }

    public static LocalTime parseTime(String value, String name) {
        String v = Validation.requireNonBlank(value, name);
        try {
            return LocalTime.parse(v, TF);
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid time format. Expected HH:mm");
        }
    }
}
