package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j

public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    //private final List<Film> filmsList = new ArrayList<>();
    private int nextId = 1;


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return new ArrayList<>(films.values());
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");

        if (films.containsKey(film.getId())) {
          films.put(film.getId(), film);
      } else {
            throw new ValidException("Фильм с указанным id не найден");
        }
         return film;
    }
}