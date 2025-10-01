package ex1.structural.decorator;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> store = new HashMap<>();

    @Override
    public void save(User user) {
        if (user == null || user.id == null) return;
        store.put(user.id, user);
    }

    @Override
    public User getById(String id) {
        return store.get(id);
    }
}
