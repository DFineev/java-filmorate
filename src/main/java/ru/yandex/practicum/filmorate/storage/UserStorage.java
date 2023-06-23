package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

    User getUserById(int id);
}
