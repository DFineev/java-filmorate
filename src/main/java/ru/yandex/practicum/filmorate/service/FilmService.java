package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    public void makeLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        HashSet<Integer> likes = film.getLikes();
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);
    }

    public void removeLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        HashSet<Integer> likes = film.getLikes();
        likes.remove(userId);
        film.setLikes(likes);
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
            if (film1.getLikes()==null && film2.getLikes()== null) {
                return 0;
            }
           if (film1.getLikes()== null) {
                return -1;
            }
            if (film2.getLikes() == null){
                return 1;
            } else {
                return Integer.compare(film1.getLikes().size(), film2.getLikes().size());
            }
        }
    }
}
