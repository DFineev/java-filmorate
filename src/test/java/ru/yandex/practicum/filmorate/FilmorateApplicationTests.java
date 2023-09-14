package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.UserDbStorage;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJdbcTest
@Sql(value = {"/schematest.sql", "/datatest.sql"})

public class FilmorateApplicationTests {

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userStorage;

    @Autowired
    public FilmorateApplicationTests(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userStorage = new UserDbStorage(jdbcTemplate);
    }

    @Test
    public void shouldFindUserById() {
       Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(1));

        assertThat(userOptional)
              .isPresent()
           .hasValueSatisfying(user ->
                 assertThat(user).hasFieldOrPropertyWithValue("id", 1)
           );
    }
}
