package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.FilmDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class FilmorateApplicationTests {

    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    Mpa mpa1 = new Mpa(1, "G");
    Mpa mpa2 = new Mpa(2, "PG");
    Genre genre1 = new Genre(1, "Комедия");

    @Test
    public void shouldCreateUSerAndFindById() {

        User user = new User("mail@mail.ru", "dolore", "Nick Name",
                LocalDate.of(1946, 8, 20));
        Integer id = userStorage.createUser(user).getId();

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(id));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", id)
                );
    }

    @Test
    public void shouldCompleteOperationsWithFilm() {
        Film film = new Film("nisi eiusmod", "adipisicing", LocalDate.of(1967, 8, 25), 100, mpa1);
        Integer id = filmStorage.addFilm(film).getId();

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(id));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("name", "nisi eiusmod")
                                .hasFieldOrPropertyWithValue("description", "adipisicing"));


        film.setName("Film Updated");
        film.setDescription("New film update decription");
        film.setDuration(190);
        film.setMpa(mpa2);
        filmStorage.updateFilm(film);

        filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("name", "Film Updated")
                                .hasFieldOrPropertyWithValue("description", "New film update decription")
                                .hasFieldOrPropertyWithValue("duration", 190)
                                .hasFieldOrPropertyWithValue("mpa", mpa2));

    }
}
