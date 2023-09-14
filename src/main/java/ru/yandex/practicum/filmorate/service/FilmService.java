package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component
@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public void removeFilm(int id) {
        filmStorage.removeFilm(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }


    public void makeLike(int id, int userId) {
       /* Film film = filmStorage.getFilmById(id);
        Set<Integer> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);*/
        filmStorage.setLike(id, userId);
    }

    public void removeLike(Integer id, Integer userId) {
    /*    Film film = filmStorage.getFilmById(id);
        Set<Integer> likes = film.getLikes();
        if (likes == null || !likes.contains(userId)) {
            throw new IncorrectParameterException("userId");
        }
        likes.remove(userId);
        film.setLikes(likes);*/
        filmStorage.deleteLike(id, userId);
    }

    public List<Film> getFilmsChart(Integer size) {
        List<Film> filmList = new ArrayList<>(filmStorage.getFilms());
        LikesComparator likesComparator = new LikesComparator();
        filmList.sort(likesComparator.reversed());
        if (filmList.size() > size) {
            filmList = filmList.subList(0, size);
        }
        return filmList;
    }

    public static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            if (film1.getLikes() == null && film2.getLikes() == null) {
                return 0;
            }
            if (film1.getLikes() == null) {
                return -1;
            }
            if (film2.getLikes() == null) {
                return 1;
            } else {
                return Integer.compare(film1.getLikes().size(), film2.getLikes().size());
            }
        }
    }
}
