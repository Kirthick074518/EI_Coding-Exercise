package ex1.creational.builder;

public final class BuilderMain {
    public static void main(String[] args) {
        System.out.println("-- Builder Pattern --");
        Report report = new Report.Builder()
                .title("Mission Brief: Europa Survey")
                .author("Control Center")
                .addSection("Objective", "Perform orbital survey and identify potential landing zones.")
                .addSection("Safety", "Maintain safe distance; monitor radiation levels.")
                .putMeta("region", "Jupiter System")
                .putMeta("priority", "high")
                .build();

        String text = new TextReportFormatter().format(report);
        String json = new JsonReportFormatter().format(report);

        System.out.println("[Text]\n" + text);
        System.out.println("[JSON]\n" + json + "\n");
    }
}
