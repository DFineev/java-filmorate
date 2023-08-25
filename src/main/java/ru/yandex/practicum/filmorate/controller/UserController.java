package ru.yandex.practicum.filmorate.controller;

//import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Component
@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validator(user);
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос списка пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable @Min(1) int userId) {
        log.info("Получен запрос на поиск пользователя по id");
        return userService.getUserById(userId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос на изменение пользователя");
        validator(user);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") @Min(1) int id) {
        log.info("Получен запрос на удаление пользователя");
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на добавление в друзья");
        userService.makeFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable @Min(1) int id, @PathVariable @Min(1) int friendId) {
        log.info("Получен запрос на удаление из друзей");
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable int id) {
        log.info("Получен запрос на вывод списка друзей");
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsList(@PathVariable @Min(1) int id, @PathVariable @Min(1) int otherId) {
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
