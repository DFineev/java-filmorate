package ru.yandex.practicum.filmorate.storage;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.constraints.Positive;
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
            if (film.getId() < 1) {
                throw new ValidException("ID меньше 1");
            } else {
                throw new ValidException("Фильм с указанным id не найден");
            }
        }
        return film;
    }

    @Override
    public void removeFilm(int id) {
        if (films.containsKey(id)) {
            films.remove(id);
        } else {
            throw new ValidException("Фильм с указанным id не найден");
        }
    }

    @Override
    public HashSet<Film> getFilms() {
        return new HashSet<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new IncorrectParameterException("id");
        }
        return films.get(id);
    }
}
