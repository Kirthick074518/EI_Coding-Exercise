package com.kirthick.dp.creational.builder;

import com.kirthick.dp.app.ConsoleIO;
import com.kirthick.dp.common.Validation;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class BuilderDemo {
    public static void run(ConsoleIO io) {
        io.println("-- Builder Pattern: Report Builder --");
        Report.Builder b = new Report.Builder();

        String title = io.readLine("Title: ");
        String author = io.readLine("Author: ");
        b.title(Validation.requireNonBlank(title, "title")).author(Validation.requireNonBlank(author, "author"));

        io.println("Add sections (enter blank title to stop)");
        AtomicInteger count = new AtomicInteger(1);
        Stream.generate(() -> io.readLine("Section " + count.getAndIncrement() + " title: "))
                .takeWhile(Objects::nonNull)
                .map(s -> s == null ? "" : s.trim())
                .takeWhile(s -> !s.isEmpty())
                .forEach(secTitle -> {
                    String content = io.readLine("Content: ");
                    b.addSection(secTitle, Validation.requireNonBlank(content, "content"));
                });

        io.println("Add metadata key=value pairs (blank to stop)");
        Stream.generate(() -> io.readLine("meta > "))
                .takeWhile(Objects::nonNull)
                .map(String::trim)
                .takeWhile(s -> !s.isEmpty())
                .forEach(line -> {
                    int idx = line.indexOf('=');
                    if (idx <= 0 || idx == line.length() - 1) {
                        io.println("Invalid format. Use key=value");
                        return;
                    }
                    String k = line.substring(0, idx).trim();
                    String v = line.substring(idx + 1).trim();
                    b.putMeta(k, v);
                });

        String fmt = io.readLine("Formatter (text/json): ");
        ReportFormatter formatter = "json".equalsIgnoreCase(fmt) ? new JsonReportFormatter() : new TextReportFormatter();
        try {
            Report r = b.build();
            String out = formatter.format(r);
            io.println("\nFormatted (" + formatter.name() + "):\n" + out);
        } catch (Exception e) {
            io.println("Failed to build report: " + e.getMessage());
        }
    }
}
