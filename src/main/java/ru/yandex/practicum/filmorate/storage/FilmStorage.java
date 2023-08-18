package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void removeFilm(int id);

    List<Film> getFilms();

    Film getFilmById(int id);
    Film setLike(Integer filmId, Integer userId);
    Film deleteLike(Integer filmId, Integer userId);

}
