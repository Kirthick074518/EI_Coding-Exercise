package com.kirthick.astro.schedule.manager;

import com.kirthick.astro.common.Validation;
import com.kirthick.astro.common.exceptions.ConflictException;
import com.kirthick.astro.common.exceptions.NotFoundException;
import com.kirthick.astro.common.exceptions.ValidationException;
import com.kirthick.astro.schedule.domain.Priority;
import com.kirthick.astro.schedule.domain.Task;
import com.kirthick.astro.schedule.observer.ScheduleEvent;
import com.kirthick.astro.schedule.observer.ScheduleObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class ScheduleManager {
    private static final class Holder { static final ScheduleManager INSTANCE = new ScheduleManager(); }
    public static ScheduleManager getInstance() { return Holder.INSTANCE; }

    private final Map<String, Task> tasks = new ConcurrentHashMap<>(); // key: normalized description
    private final CopyOnWriteArrayList<ScheduleObserver> observers = new CopyOnWriteArrayList<>();

    private ScheduleManager() {}

    public void addObserver(ScheduleObserver obs) { if (obs != null) observers.addIfAbsent(obs); }
    public void removeObserver(ScheduleObserver obs) { observers.remove(obs); }

    private void notifyAllObservers(ScheduleEvent event) {
        for (ScheduleObserver o : observers) {
            try { o.onEvent(event); } catch (Exception ignored) {}
        }
    }

    private static String norm(String description) {
        return Validation.requireNonBlank(description, "description").toLowerCase(Locale.ROOT);
    }

    public synchronized void clearAll() { tasks.clear(); }

    public synchronized void add(Task task) {
        Objects.requireNonNull(task);
        String key = norm(task.description());
        if (tasks.containsKey(key)) {
            throw new ValidationException("Task with this description already exists");
        }
        Task conflict = findConflict(task, null);
        if (conflict != null) {
            notifyAllObservers(ScheduleEvent.conflict(task, conflict));
            throw new ConflictException("Task conflicts with existing task \"" + conflict.description() + "\"");
        }
        tasks.put(key, task);
        notifyAllObservers(ScheduleEvent.added(task));
    }

    public synchronized Task remove(String description) {
        String key = norm(description);
        Task removed = tasks.remove(key);
        if (removed == null) {
            throw new NotFoundException("Task not found");
        }
        notifyAllObservers(ScheduleEvent.removed(removed));
        return removed;
    }

    public synchronized List<Task> viewAllSorted() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::start))
                .collect(Collectors.toList());
    }

    public synchronized List<Task> viewByPriority(Priority priority) {
        return tasks.values().stream()
                .filter(t -> t.priority() == priority)
                .sorted(Comparator.comparing(Task::start))
                .collect(Collectors.toList());
    }

    public synchronized Task findByDescription(String description) {
        return tasks.get(norm(description));
    }

    public synchronized Task markCompleted(String description) {
        String key = norm(description);
        Task existing = tasks.get(key);
        if (existing == null) throw new NotFoundException("Task not found");
        if (existing.isCompleted()) return existing; // idempotent
        Task updated = existing.withCompleted(true);
        tasks.put(key, updated);
        notifyAllObservers(ScheduleEvent.completed(updated));
        return updated;
    }

    public synchronized Task edit(String existingDescription, String newDescription, LocalTime newStart,
                                  LocalTime newEnd, Priority newPriority) {
        String key = norm(existingDescription);
        Task existing = tasks.get(key);
        if (existing == null) throw new NotFoundException("Task not found");

        String desc = (newDescription == null || newDescription.isBlank()) ? existing.description() : newDescription.trim();
        LocalTime st = (newStart == null) ? existing.start() : newStart;
        LocalTime et = (newEnd == null) ? existing.end() : newEnd;
        Priority pr = (newPriority == null) ? existing.priority() : newPriority;

        // If description changed, ensure uniqueness
        String newKey = norm(desc);
        if (!newKey.equals(key) && tasks.containsKey(newKey)) {
            throw new ValidationException("Task with this description already exists");
        }
        Task candidate = new Task(desc, st, et, pr, existing.isCompleted());
        Task conflict = findConflict(candidate, key);
        if (conflict != null) {
            notifyAllObservers(ScheduleEvent.conflict(candidate, conflict));
            throw new ConflictException("Task conflicts with existing task \"" + conflict.description() + "\"");
        }
        // Persist
        if (!newKey.equals(key)) tasks.remove(key);
        tasks.put(newKey, candidate);
        notifyAllObservers(ScheduleEvent.updated(candidate));
        return candidate;
    }

    private Task findConflict(Task candidate, String ignoreKey) {
        for (Map.Entry<String, Task> e : new ArrayList<>(tasks.entrySet())) {
            if (ignoreKey != null && e.getKey().equals(ignoreKey)) continue;
            Task existing = e.getValue();
            // Completed tasks do not block new scheduling
            if (existing.isCompleted()) continue;
            if (overlaps(candidate, existing)) return existing;
        }
        return null;
    }

    private static boolean overlaps(Task a, Task b) {
        return a.start().isBefore(b.end()) && b.start().isBefore(a.end());
    }
}
