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

    public User createUser(User user){
        return userStorage.createUser(user);
    }

    public List<User> getUsers(){
        return userStorage.getUsers();
    }

    public User getUserById(int id){
        return userStorage.getUserById(id);
    }

    public User updateUser(User user){
        return userStorage.updateUser(user);
    }

    public void deleteUser(int id){
        userStorage.deleteUser(id);
    }
    public void makeFriends(int id, int friendId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(friendId);
        Set<Integer> friends1 = user1.getFriends();

        if (friends1 == null) {
            friends1 = new HashSet<>();
        }
        friends1.add(user2.getId());
        user1.setFriends(friends1);

        Set<Integer> friends2 = user2.getFriends();
        if (friends2 == null) {
            friends2 = new HashSet<>();
        }
        friends2.add(user1.getId());
        user2.setFriends(friends2);
    }

    public void deleteFromFriends(int id, int friendId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(friendId);
        Set<Integer> friends1 = user1.getFriends();
        friends1.remove(user2.getId());
        user1.setFriends(friends1);
        Set<Integer> friends2 = user2.getFriends();
        friends2.remove(user1.getId());
        user2.setFriends(friends2);
    }

    public List<User> getFriendsList(Integer id) {
        User user = userStorage.getUserById(id);
        List<User> friendsList = new ArrayList<>();
        UsersComparator usersComparator = new UsersComparator();
        Set<Integer> friendIds = user.getFriends();
        if (friendIds == null) {
            return friendsList;
        } else {
            for (Integer friendId : friendIds) {
                friendsList.add(userStorage.getUserById(friendId));
            }
            friendsList.sort(usersComparator);
        }
        return friendsList;
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

