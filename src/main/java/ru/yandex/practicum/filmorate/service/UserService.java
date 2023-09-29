package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Component
@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

    public void makeFriends(int id, int friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void deleteFromFriends(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(Integer id) {
        return userStorage.getFriendsByUserId(id);

    }

    public List<User> getCommonFriendsList(int id, int otherId) {
        List<User> commonFriendsList = new ArrayList<>();
        UsersComparator usersComparator = new UsersComparator();
        List<User> friends1List = getFriendsList(id);
        List<User> friends2List = getFriendsList(otherId);
        for (User user : friends1List) {
            for (User user1 : friends2List) {
                if (user.getId() == user1.getId()) {
                    commonFriendsList.add(user);
                }
            }
        }
        commonFriendsList.sort(usersComparator.reversed());
        return commonFriendsList;
    }

    public static class UsersComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return Integer.compare(user1.getId(), user2.getId());
        }
    }
}

