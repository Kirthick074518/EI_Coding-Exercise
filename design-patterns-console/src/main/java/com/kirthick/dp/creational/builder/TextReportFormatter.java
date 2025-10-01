package com.kirthick.dp.creational.builder;

public final class TextReportFormatter implements ReportFormatter {
    @Override
    public String name() { return "text"; }

    @Override
    public String format(Report r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Report: ").append(r.title).append('\n');
        sb.append("Author: ").append(r.author).append("  Created: ").append(r.createdAt).append('\n');
        if (!r.metadata.isEmpty()) {
            sb.append("Metadata:\n");
            r.metadata.forEach((k, v) -> sb.append("  - ").append(k).append(": ").append(v).append('\n'));
        }
        sb.append("Sections:\n");
        for (int i = 0; i < r.sections.size(); i++) {
            var s = r.sections.get(i);
            sb.append("  ").append(i + 1).append(") ").append(s.title).append('\n');
            sb.append("     ").append(s.content).append('\n');
        }
        return sb.toString();
    }
}
