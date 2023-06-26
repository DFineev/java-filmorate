package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
@Validated
public class FilmController {

    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    FilmService filmService = new FilmService(inMemoryFilmStorage);


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        return inMemoryFilmStorage.addFilm(film);
    }

    @GetMapping
    public HashSet<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return inMemoryFilmStorage.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable("filmId") @Min(1) int id) {
        log.info("Получен запрос на поиск фильма");
        return inMemoryFilmStorage.getFilmById(id);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");
        return inMemoryFilmStorage.updateFilm(film);
    }

    @DeleteMapping("/{filmId}")
    public void remove(@PathVariable("filmId") @Min(1) int id) {
        log.info("Получен запрос на удаление фильма");
        inMemoryFilmStorage.removeFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void makeLike(@PathVariable("id") @Min(1) int id, @PathVariable("userId") @Min(1) int userId) {
        log.info("Получен запрос на добавление лайка");
        filmService.makeLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") @Min(1) int id, @PathVariable("userId") @Min(1) int userId) {
        log.info("Получен запрос на удаление лайка");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос списка самых популярных фильмов");
        return filmService.getFilmsChart(count);
    }
}