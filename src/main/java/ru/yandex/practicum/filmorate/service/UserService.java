package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;
public void makeFriends(int id, int friendId){
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

public void deleteFromFriends(int id, int friendId){
    User user1 = userStorage.getUserById(id);
    User user2 = userStorage.getUserById(friendId);
    Set<Integer> friends1 = user1.getFriends();
    friends1.remove(user2.getId());
    user1.setFriends(friends1);
    Set<Integer> friends2 = user2.getFriends();
    friends2.remove(user1.getId());
    user2.setFriends(friends2);
}
public Set<User> getFriendsList(int id){
    User user = userStorage.getUserById(id);
    Set<Integer> friendIds = user.getFriends();
    Set<User> friendsList = new HashSet<>();
    for (Integer friendId : friendIds) {
        friendsList.add(userStorage.getUserById(friendId));
    }
    return friendsList;
}
public Set<User> getCommonFriendsList(int id, int otherId){
    Set<User> commonFriendsList = new HashSet<>();
    Set<User> friends1List = getFriendsList(id);
    Set<User> friends2List = getFriendsList(otherId);
    for (User user : friends1List) {
        for (User user1 : friends2List) {
            if (user.getId()==user1.getId()) {
                commonFriendsList.add(user);
            }
        }
    }
    return commonFriendsList;
}
}
