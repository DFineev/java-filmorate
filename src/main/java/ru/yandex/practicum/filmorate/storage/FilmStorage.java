package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm (Film film);

    void removeFilm (int id);
    HashSet<Film> getFilms();

    Film getFilmById(int id);

}
