package ru.yandex.practicum.filmorate.controller;

//import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

        private final Map<Integer, User> users = new HashMap<>();
        public int nextId = 1;



    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя");
        validator(user);
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос списка пользователей");
        return new ArrayList<>(users.values());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос на изменение пользователя");
        validator(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidException("Пользователь не найден");
        }
        users.put(user.getId(), user);
        return user;
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
