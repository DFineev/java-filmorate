package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;

@Component
@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {


   private final FilmService filmService;
    
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        return filmService.addFilm(film);
    }

    @GetMapping
    public HashSet<Film> getFilms() {
        log.info("Получен запрос списка фильмов");
        return filmService.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable("filmId") @Min(1) int id) {
        log.info("Получен запрос на поиск фильма");
        return filmService.getFilmById(id);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма");
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{filmId}")
    public void remove(@PathVariable("filmId") @Min(1) int id) {
        log.info("Получен запрос на удаление фильма");
        filmService.removeFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void makeLike(@PathVariable("id") @Min(1) int id, @PathVariable("userId") @Min(1) int userId) {
        log.info("Получен запрос на добавление лайка");
        filmService.makeLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("Получен запрос на удаление лайка");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос списка самых популярных фильмов");
        return filmService.getFilmsChart(count);
    }
}