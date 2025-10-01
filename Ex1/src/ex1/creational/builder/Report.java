package ex1.creational.builder;

import java.util.*;

public final class Report {
    public static final class Section {
        public final String title;
        public final String content;
        public Section(String title, String content) {
            this.title = title; this.content = content;
        }
    }

    public static final class Builder {
        private String title;
        private String author;
        private final List<Section> sections = new ArrayList<>();
        private final LinkedHashMap<String, String> metadata = new LinkedHashMap<>();

        public Builder title(String t) { this.title = Objects.requireNonNullElse(t, "Untitled"); return this; }
        public Builder author(String a) { this.author = Objects.requireNonNullElse(a, "Unknown"); return this; }
        public Builder addSection(String t, String c) { sections.add(new Section(t, c)); return this; }
        public Builder putMeta(String k, String v) { if (k!=null && v!=null) metadata.put(k, v); return this; }
        public Report build() { return new Report(title, author, List.copyOf(sections), new LinkedHashMap<>(metadata)); }
    }

    public final String title;
    public final String author;
    public final List<Section> sections;
    public final Map<String, String> metadata;

    public Report(String title, String author, List<Section> sections, Map<String, String> metadata) {
        this.title = title; this.author = author; this.sections = sections; this.metadata = metadata;
    }
}
