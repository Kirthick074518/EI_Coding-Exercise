package ex1.structural.decorator;

public final class LoggingUserRepository implements UserRepository {
    private final UserRepository inner;
    public LoggingUserRepository(UserRepository inner) { this.inner = inner; }

    @Override
    public void save(User user) {
        long t0 = System.nanoTime();
        inner.save(user);
        long dt = System.nanoTime() - t0;
        System.out.println("[Repo-Log] save(" + user + ") in " + (dt/1_000_000) + " ms");
    }

    @Override
    public User getById(String id) {
        long t0 = System.nanoTime();
        User u = inner.getById(id);
        long dt = System.nanoTime() - t0;
        System.out.println("[Repo-Log] getById(" + id + ") -> " + u + " in " + (dt/1_000_000) + " ms");
        return u;
    }
}
