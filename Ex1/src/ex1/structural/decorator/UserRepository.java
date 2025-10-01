package ex1.structural.decorator;

public interface UserRepository {
    void save(User user);
    User getById(String id);
}
