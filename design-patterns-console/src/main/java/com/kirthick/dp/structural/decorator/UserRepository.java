package com.kirthick.dp.structural.decorator;

public interface UserRepository {
    User findById(String id);
    void save(User user);
}
