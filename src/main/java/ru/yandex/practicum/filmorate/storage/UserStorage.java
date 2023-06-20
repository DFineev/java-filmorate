package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    public User createUser(User user);

    public User updateUser (User user);

    public void deleteUser (int id);

    public User getUserById(int id);
}
