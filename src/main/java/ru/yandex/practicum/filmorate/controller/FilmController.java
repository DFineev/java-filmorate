package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

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

    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        return inMemoryFilmStorage.addFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
       return inMemoryFilmStorage.getFilms();
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");
        return inMemoryFilmStorage.updateFilm(film);
    }
    @DeleteMapping ("/{filmId}")
    public void remove(@PathVariable("filmId") int id){
        log.info("Получен запрос на удаление фильма");
        inMemoryFilmStorage.removeFilm(id);
    }
}