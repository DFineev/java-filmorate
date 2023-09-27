package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
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

    @Test
    public void shouldFindUserById() {

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
    public void shouldAddFilm() {
        Mpa mpa1 = new Mpa(1, "G");
        Film film = new Film("nisi eiusmod", "adipisicing", LocalDate.of(1967, 8, 25), 100, mpa1);
        Integer id = filmStorage.addFilm(film).getId();

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(id));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("name", "nisi eiusmod")
                                .hasFieldOrPropertyWithValue("description", "adipisicing"));
    }
}
