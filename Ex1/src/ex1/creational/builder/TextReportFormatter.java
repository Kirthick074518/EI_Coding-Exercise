package ex1.creational.builder;

public final class TextReportFormatter implements ReportFormatter {
    @Override
    public String format(Report r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Report: ").append(r.title).append("\n");
        sb.append("Author: ").append(r.author).append("\n\n");
        for (Report.Section s : r.sections) {
            sb.append("# ").append(s.title).append("\n");
            sb.append(s.content).append("\n\n");
        }
        if (!r.metadata.isEmpty()) {
            sb.append("Meta:\n");
            r.metadata.forEach((k, v) -> sb.append("- ").append(k).append(": ").append(v).append("\n"));
        }
        return sb.toString();
    }
}
