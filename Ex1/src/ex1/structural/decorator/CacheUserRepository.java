package ex1.structural.decorator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheUserRepository implements UserRepository {
    private final UserRepository inner;
    private final Map<String, User> cache = new ConcurrentHashMap<>();

    public CacheUserRepository(UserRepository inner) { this.inner = inner; }

    @Override
    public void save(User user) {
        inner.save(user);
        if (user != null && user.id != null) cache.put(user.id, user);
    }

    @Override
    public User getById(String id) {
        User u = cache.get(id);
        if (u != null) {
            System.out.println("[Repo-Cache] hit for id=" + id);
            return u;
        }
        System.out.println("[Repo-Cache] miss for id=" + id);
        u = inner.getById(id);
        if (u != null) cache.put(id, u);
        return u;
    }
}
