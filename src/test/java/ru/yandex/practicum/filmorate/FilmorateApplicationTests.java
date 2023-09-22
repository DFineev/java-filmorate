package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.DAO.UserDbStorage;

@SpringBootTest
//@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class FilmorateApplicationTests {

    private final UserDbStorage userStorage;

    @Test
    public void shouldFindUserById() {
/*
        User user = new User("mail@mail.ru", "dolore", "Nick Name",
                LocalDate.of(1946, 8, 20));
        Integer id = userStorage.createUser(user).getId();

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(id));

        assertThat(userOptional)
              .isPresent()
            .hasValueSatisfying(u ->
                  assertThat(u).hasFieldOrPropertyWithValue("id", id)
         );*/
    }
}
