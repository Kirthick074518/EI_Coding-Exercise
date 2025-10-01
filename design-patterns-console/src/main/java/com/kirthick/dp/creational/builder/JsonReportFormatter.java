package com.kirthick.dp.creational.builder;

import java.util.stream.Collectors;

public final class JsonReportFormatter implements ReportFormatter {
    @Override
    public String name() { return "json"; }

    @Override
    public String format(Report r) {
        String sections = r.sections.stream()
                .map(s -> String.format("{\"title\":\"%s\",\"content\":\"%s\"}", esc(s.title), esc(s.content)))
                .collect(Collectors.joining(","));
        String metadata = r.metadata.entrySet().stream()
                .map(e -> String.format("\"%s\":\"%s\"", esc(e.getKey()), esc(e.getValue())))
                .collect(Collectors.joining(","));
        return String.format("{\n  \"title\": \"%s\",\n  \"author\": \"%s\",\n  \"createdAt\": \"%s\",\n  \"metadata\": { %s },\n  \"sections\": [ %s ]\n}",
                esc(r.title), esc(r.author), esc(r.createdAt.toString()), metadata, sections);
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
