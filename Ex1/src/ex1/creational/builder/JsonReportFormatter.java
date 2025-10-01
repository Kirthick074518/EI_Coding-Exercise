package ex1.creational.builder;

public final class JsonReportFormatter implements ReportFormatter {
    @Override
    public String format(Report r) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"title\":\"").append(escape(r.title)).append("\",");
        sb.append("\"author\":\"").append(escape(r.author)).append("\",");
        sb.append("\"sections\":[");
        for (int i = 0; i < r.sections.size(); i++) {
            Report.Section s = r.sections.get(i);
            if (i > 0) sb.append(',');
            sb.append('{')
              .append("\"title\":\"").append(escape(s.title)).append("\",")
              .append("\"content\":\"").append(escape(s.content)).append("\"}");
        }
        sb.append(']');
        if (!r.metadata.isEmpty()) {
            sb.append(',').append("\"metadata\":{");
            int j = 0;
            for (var e : r.metadata.entrySet()) {
                if (j++ > 0) sb.append(',');
                sb.append("\"").append(escape(e.getKey())).append("\":\"")
                  .append(escape(e.getValue())).append("\"");
            }
            sb.append('}');
        }
        sb.append('}');
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
