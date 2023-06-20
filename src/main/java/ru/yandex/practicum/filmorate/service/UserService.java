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
public Set<Integer> getFriendsList(int id){
    User user = userStorage.getUserById(id);
    return user.getFriends();
}
}
