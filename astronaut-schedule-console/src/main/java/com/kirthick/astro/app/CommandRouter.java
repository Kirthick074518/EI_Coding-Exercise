package com.kirthick.astro.app;

import com.kirthick.astro.common.exceptions.AppException;
import com.kirthick.astro.common.exceptions.ValidationException;
import com.kirthick.astro.schedule.domain.Priority;
import com.kirthick.astro.schedule.domain.Task;
import com.kirthick.astro.schedule.factory.TaskFactory;
import com.kirthick.astro.schedule.manager.ScheduleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.*;

public final class CommandRouter {
    private static final Logger log = LoggerFactory.getLogger(CommandRouter.class);

    private final Map<String, CommandHandler> handlers = new LinkedHashMap<>();
    private final List<CommandMeta> metaList = new ArrayList<>();
    private final ConsoleIO io;
    private final ScheduleManager manager = ScheduleManager.getInstance();

    public CommandRouter(ConsoleIO io) {
        this.io = io;
        register("help", this::help, "Show available commands");
        register("clear", this::clear, "Clear the console");
        register("add", this::addTask, "Add a new task");
        register("remove", this::removeTask, "Remove an existing task by description");
        register("view", this::viewTasks, "View all tasks sorted by start time");
        register("viewp", this::viewByPriority, "View tasks filtered by priority (HIGH/MEDIUM/LOW)");
        register("edit", this::editTask, "Edit an existing task (times HH:mm; leave blank to keep)");
        register("complete", this::completeTask, "Mark a task as completed");
        register("exit", args -> io.println("Goodbye!"), "Exit the application");
    }

    public interface CommandHandler { void handle(String[] args); }

    private static final class CommandMeta {
        final String name; final String description; final CommandHandler handler;
        CommandMeta(String name, String description, CommandHandler handler) {
            this.name = name; this.description = description; this.handler = handler;
        }
    }

    private void register(String name, CommandHandler handler, String description) {
        Objects.requireNonNull(name); Objects.requireNonNull(handler);
        handlers.put(name.toLowerCase(Locale.ROOT), handler);
        metaList.add(new CommandMeta(name.toLowerCase(Locale.ROOT), description, handler));
    }

    public boolean isExitCommand(String line) { return line != null && line.equalsIgnoreCase("exit"); }

    public void route(String line) {
        if (line == null || line.isBlank()) return;
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        CommandHandler h = handlers.get(command);
        if (h == null) {
            io.println("Unknown command: " + command + " (type 'help' to see commands)");
            return;
        }
        try { h.handle(args); }
        catch (AppException ex) { io.println("Error: " + ex.getMessage()); }
        catch (Exception ex) { log.error("Command '{}' failed", command, ex); io.println("Command failed: " + ex.getMessage()); }
    }

    private void help(String[] args) {
        io.println("Available commands:");
        for (CommandMeta m : metaList) {
            io.println("  - " + String.format("%-10s", m.name) + " : " + m.description);
        }
    }

    private void clear(String[] args) {
        try {
            if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) io.println("\n".repeat(50));
            else { System.out.print("\033[H\033[2J"); System.out.flush(); }
        } catch (Exception e) { io.println("\n".repeat(50)); }
    }

    private void addTask(String[] args) {
        String desc = io.readLine("Description: ");
        String st = io.readLine("Start time (HH:mm): ");
        String et = io.readLine("End time (HH:mm): ");
        String pr = io.readLine("Priority (High/Medium/Low): ");
        Task t = TaskFactory.create(desc, st, et, pr);
        manager.add(t);
        io.println("Task added successfully. No conflicts.");
    }

    private void removeTask(String[] args) {
        String desc = io.readLine("Description of task to remove: ");
        manager.remove(desc);
        io.println("Task removed successfully.");
    }

    private void viewTasks(String[] args) {
        List<Task> list = manager.viewAllSorted();
        if (list.isEmpty()) { io.println("No tasks scheduled for the day."); return; }
        int i = 1;
        for (Task t : list) io.println(String.format("%d. %s", i++, t));
    }

    private void viewByPriority(String[] args) {
        String pr = io.readLine("Priority (High/Medium/Low): ");
        Priority p = Priority.from(pr);
        List<Task> list = manager.viewByPriority(p);
        if (list.isEmpty()) { io.println("No tasks for priority " + p + "."); return; }
        int i = 1;
        for (Task t : list) io.println(String.format("%d. %s", i++, t));
    }

    private void completeTask(String[] args) {
        String desc = io.readLine("Description: ");
        manager.markCompleted(desc);
        io.println("Task marked completed.");
    }

    private void editTask(String[] args) {
        String existing = io.readLine("Existing description: ");
        String newDesc = io.readLine("New description (blank=keep): ");
        String st = io.readLine("New start (HH:mm, blank=keep): ");
        String et = io.readLine("New end (HH:mm, blank=keep): ");
        String pr = io.readLine("New priority (High/Medium/Low, blank=keep): ");

        LocalTime newStart = st == null || st.isBlank() ? null : TaskFactory.parseTime(st, "start time");
        LocalTime newEnd = et == null || et.isBlank() ? null : TaskFactory.parseTime(et, "end time");
        Priority newPr = pr == null || pr.isBlank() ? null : Priority.from(pr);

        Task updated = manager.edit(existing, newDesc, newStart, newEnd, newPr);
        io.println("Task updated: " + updated);
    }
}
