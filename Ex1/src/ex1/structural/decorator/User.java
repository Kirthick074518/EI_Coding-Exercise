package ex1.structural.decorator;

public final class User {
    public final String id;
    public final String name;
    public User(String id, String name) {
        this.id = id; this.name = name;
    }
    @Override
    public String toString() { return "User{id='" + id + "', name='" + name + "'}"; }
}
