package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();


    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validator(user);
        user.setId(UUID.randomUUID().hashCode() & Integer.MAX_VALUE);
        users.put(user.getId(), user);
        System.out.println(user);
        return user;
    }

    @GetMapping
    public Map<Integer, User> getUsers() {
        log.info("Получен запрос списка пользователей");
        return users;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос на изменение пользователя");
        validator(user);
        users.put(user.getId(), user);
        return user;
    }

    public void validator(User user) {
         if (user.getLogin().contains(" ")) {
             log.info("Валидация не пройдена");
            throw new ValidException("Логин не может содержать пробелы");
        }
    }
}
