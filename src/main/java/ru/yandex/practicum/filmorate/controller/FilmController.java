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
        boolean isValid = false;
        for (Film film1 : filmsList) {
            if (film1.getId()==film.getId()) {
              isValid = true;
                filmsList.set(filmsList.indexOf(film1), film );
            }
        }
         if (!isValid) {
             throw new ValidException("Фильм с указанным id не найден");
         }
return film;

    }


}