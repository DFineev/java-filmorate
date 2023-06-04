package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //validator(film);
        film.setId(nextId++);
        filmsList.add(film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return filmsList;


    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма"); //todo: разобраться с этим методо

        boolean idValidator = false;
        for (Film film1 : filmsList) {
            if (film1.getId() == film.getId()) {
                idValidator = true;
            }
                        }
        if (!idValidator) {
            throw new ValidException("Фильм с указанным айди не найден");
        } else {
            filmsList.set(film.getId()-1, film);
        }
return film;

    }


}