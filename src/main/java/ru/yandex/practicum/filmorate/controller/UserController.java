package ru.yandex.practicum.filmorate.controller;

//import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {


    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    UserService userService = new UserService(inMemoryUserStorage);


    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validator(user);
        return inMemoryUserStorage.createUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос списка пользователей");
        return inMemoryUserStorage.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("Получен запрос на поиск пользователя по id");

        return inMemoryUserStorage.getUserById(userId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос на изменение пользователя");
        validator(user);
        return inMemoryUserStorage.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") int id) {
        log.info("Получен запрос на удаление пользователя");
        inMemoryUserStorage.deleteUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@Valid @PathVariable Integer id,@Valid @PathVariable Integer friendId) {
        log.info("Получен запрос на добавление в друзья");
        userService.makeFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен запрос на удаление из друзей");
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriendsList(@PathVariable int id) {
        log.info("Получен запрос на вывод списка друзей");
        return userService.getFriendsList(id);
    }
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId){
        log.info("Получен запрос списка общих друзей");
        return userService.getCommonFriendsList(id, otherId);
    }

    public void validator(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            log.info("Валидация не пройдена");
            throw new ValidException("Логин не может содержать пробелы");
        }
    }
}
