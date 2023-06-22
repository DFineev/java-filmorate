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
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен запрос на добавление в друзья");
        userService.makeFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен запрос на удаление из друзей");
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<Integer> getFriendsList(@PathVariable int id) {
        log.info("Получен запрос на вывод списка друзей");
        return userService.getFriendsList(id);
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
