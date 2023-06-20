package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
private final FilmStorage filmStorage;

public void makeLike(int id){
    Film film = filmStorage.getFilmById(id);
    film.setLikes(film.getLikes()+1);

}
}
