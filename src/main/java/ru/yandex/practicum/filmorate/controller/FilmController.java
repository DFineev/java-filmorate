package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j

public class FilmController {
    private final List<Film> filmsList = new ArrayList<>();
    private int nextId = 1;


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        film.setId(nextId++);
        filmsList.add(film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return filmsList;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");
     try {
        filmsList.set((film.getId()-1), film);
        } catch (ValidException e) {
         e.getMessage();
     }

return film;

    }


}