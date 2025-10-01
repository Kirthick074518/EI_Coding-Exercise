package com.kirthick.dp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ConsoleIO implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(ConsoleIO.class);
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        try {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                return line == null ? null : line.trim();
            }
            return null;
        } catch (IllegalStateException | NoSuchElementException e) {
            log.warn("Console input stream closed", e);
            return null;
        }
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s);
    }

    @Override
    public void close() {
        try {
            scanner.close();
        } catch (Exception ignored) { }
    }
}
