package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    public int nextId = 1;

    @Override
    public User createUser(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ObjectNotFoundException("id = " + user.getId());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        if (users.containsKey(id)) {
            users.remove(id);
        } else {
            throw new ObjectNotFoundException("id = " + id);
        }
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("id = " + id);
        }
        return users.get(id);
    }

    @Override
    public List<User> getCommonsFriends(int id, int otherId) {
        return null;
    }

    @Override
    public User addFriend(int userId, int friendId) {
        return null;
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        return null;
    }

    @Override
    public List<User> getFriendsByUserId(int id) {
        return null;
    }

}

