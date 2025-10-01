package ex1.behavioral.observer;

public final class MobileAppSubscriber implements Subscriber {
    private final String username;
    public MobileAppSubscriber(String username) { this.username = username; }
    @Override
    public void onNews(NewsCategory category, String headline) {
        System.out.println("[Mobile] to @" + username + ": (" + category + ") " + headline);
    }
}
