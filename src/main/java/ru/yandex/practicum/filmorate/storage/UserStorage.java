package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

    List<User> getUsers();

    User getUserById(int id);

    User addFriend(int userId, int friendId);

    User deleteFriend(int userId, int friendId);

    List<User> getFriendsByUserId(int id);

    List<User> getCommonsFriends(int id, int otherId);

}
