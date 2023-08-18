package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ObjectNotFoundException("id = " + film.getId());
        }
        return film;
    }

    @Override
    public void removeFilm(int id) {
        if (films.containsKey(id)) {
            films.remove(id);
        } else {
            throw new ObjectNotFoundException("id = " + id);
        }
    }

    @Override
    public List getFilms() {
        return new ArrayList(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new IncorrectParameterException("id");
        }
        return films.get(id);
    }

    @Override
    public Film setLike(Integer filmId, Integer userId) {
        return null;
    }

    @Override
    public Film deleteLike(Integer filmId, Integer userId) {
        return null;
    }
}
