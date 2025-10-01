package ex1.behavioral.observer;

public final class EmailSubscriber implements Subscriber {
    private final String email;
    public EmailSubscriber(String email) { this.email = email; }
    @Override
    public void onNews(NewsCategory category, String headline) {
        System.out.println("[Email] to " + email + ": (" + category + ") " + headline);
    }
}
