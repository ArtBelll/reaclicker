package ru.reaclicker.dao;

import lombok.NonNull;
import ru.reaclicker.domain.User;

/**
 * Created by Artur Belogur on 16.02.17.
 */
public interface UserDao {

    long add(@NonNull User user);

    User get(@NonNull long id);
}
