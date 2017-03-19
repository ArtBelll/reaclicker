package ru.reaclicker.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Artur Belogur on 16.02.17.
 */
public class User implements Serializable, Comparable<User> {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private double score;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.score = 0;
    }

    @Override
    public boolean equals(Object o) {
        return ((User) o).getId() == id;
    }

    @Override
    public int compareTo(User o) {
        return (int)(id - o.getId());
    }
}
