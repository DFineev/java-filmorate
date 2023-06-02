package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j

public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final int D = 1;
    private int nextId = 1;


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        validator(film);
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Map<Integer, Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return films;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");
        validator(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidException("Фильм не найден");
        }
        films.put(film.getId(), film);
        return film;
    }

    public void validator(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Валидация не пройдена");
            throw new ValidException("Выбрана слишком старая дата релиза");
        }
    }
}



