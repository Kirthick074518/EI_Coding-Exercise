package com.kirthick.dp.structural.decorator;

import com.kirthick.dp.common.Validation;

public final class User {
    public final String id;
    public final String name;

    public User(String id, String name) {
        this.id = Validation.requireNonBlank(id, "id");
        this.name = Validation.requireNonBlank(name, "name");
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
