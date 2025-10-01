package com.kirthick.astro.schedule.domain;

public enum Priority {
    HIGH, MEDIUM, LOW;

    public static Priority from(String s) {
        if (s == null) return MEDIUM;
        String v = s.trim().toUpperCase();
        switch (v) {
            case "HIGH": return HIGH;
            case "LOW": return LOW;
            default: return MEDIUM;
        }
    }
}
