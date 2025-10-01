package com.kirthick.dp.creational.builder;

import com.kirthick.dp.common.Validation;

import java.time.LocalDateTime;
import java.util.*;

public final class Report {
    public static final class Section {
        public final String title;
        public final String content;
        public Section(String title, String content) {
            this.title = Validation.requireNonBlank(title, "section title");
            this.content = Validation.requireNonBlank(content, "section content");
        }
    }

    public final String title;
    public final String author;
    public final LocalDateTime createdAt;
    public final List<Section> sections;
    public final Map<String, String> metadata;

    private Report(Builder b) {
        this.title = b.title;
        this.author = b.author;
        this.createdAt = b.createdAt == null ? LocalDateTime.now() : b.createdAt;
        this.sections = List.copyOf(b.sections);
        this.metadata = Collections.unmodifiableMap(new LinkedHashMap<>(b.metadata));
    }

    public static final class Builder {
        private String title;
        private String author;
        private LocalDateTime createdAt;
        private final List<Section> sections = new ArrayList<>();
        private final Map<String, String> metadata = new LinkedHashMap<>();

        public Builder title(String title) { this.title = Validation.requireNonBlank(title, "title"); return this; }
        public Builder author(String author) { this.author = Validation.requireNonBlank(author, "author"); return this; }
        public Builder createdAt(LocalDateTime dt) { this.createdAt = Objects.requireNonNull(dt); return this; }
        public Builder addSection(String title, String content) { this.sections.add(new Section(title, content)); return this; }
        public Builder putMeta(String key, String value) {
            metadata.put(Validation.requireNonBlank(key, "meta key"), Validation.requireNonBlank(value, "meta value"));
            return this;
        }
        public Report build() {
            Validation.requireNonBlank(title, "title");
            Validation.requireNonBlank(author, "author");
            Validation.require(!sections.isEmpty(), "at least one section required");
            return new Report(this);
        }
    }
}
