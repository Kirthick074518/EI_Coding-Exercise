package ex1.behavioral.observer;

public interface Subscriber {
    void onNews(NewsCategory category, String headline);
}
