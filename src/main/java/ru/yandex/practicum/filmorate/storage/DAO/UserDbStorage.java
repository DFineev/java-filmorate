package ru.yandex.practicum.filmorate.storage.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        getValidationUser(user);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingColumns("email","login","name","birthday")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        log.info("Поступил запрос на добавление пользователя. Пользователь добавлен.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE users " +
                "SET email=?, login=?, name=?, birthday=? " +
                "WHERE user_id=?";

        int rowsCount = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());

        if (rowsCount > 0) {
            log.info("Поступил запрос на изменения пользователя. Пользователь изменён.");
            return user;
        }
        throw new ObjectNotFoundException("Пользователь не найден.");
    }

    @Override
    public void deleteUser(int id) {
        User user = getUserById(id);
        String sqlQuery =
                "DELETE " +
                        "FROM users " +
                        "WHERE user_id = ?";

        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public User getUserById(int id) {
        String sqlQuery =
                "SELECT user_id, email, login, name, birthday " +
                        "FROM users " +
                        "WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::getMapRowToUser, id);
        } catch (RuntimeException e) {
            throw new ObjectNotFoundException("Пользователь не найден.");
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM users");
        while (rowSet.next()) {
            User user = User.builder()
                    .id(rowSet.getInt("user_id"))
                    .name(rowSet.getString("name"))
                    .login(rowSet.getString("login"))
                    .email(rowSet.getString("email"))
                    .birthday(Objects.requireNonNull(rowSet.getDate("birthday")).toLocalDate())
                    .build();
            users.add(user);
        }
        return users;
    }

    @Override
    public User addFriend(int userId, int friendId) {
        try {
            getUserById(friendId);
        } catch (RuntimeException e) {
            throw new ObjectNotFoundException("Пользователь не найден.");
        }
        String sqlQuery =
                "INSERT " +
                        "INTO friends (user_id, friend_id) " +
                        "VALUES(?, ?)";

        jdbcTemplate.update(sqlQuery, userId, friendId);
        return getUserById(userId);
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        User user = getUserById(userId);
        String sqlQuery =
                "DELETE " +
                        "FROM friends " +
                        "WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sqlQuery, userId, friendId);
        return user;
    }

    @Override
    public List<User> getFriendsByUserId(int id) {
        getUserById(id);
        String sqlQuery =
                "SELECT user_id, email, login, name, birthday " +
                        "FROM users " +
                        "WHERE user_id " +
                        "IN(SELECT friend_id " +
                        "FROM friends " +
                        "WHERE user_id=?)";

        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::getMapRowToUser, id));
    }

    @Override
    public List<User> getCommonsFriends(int id, int otherId) {
        String sqlQuery =
                "SELECT user_id, email, login, name, birthday " +
                        "FROM users " +
                        "WHERE user_id " +
                        "IN(SELECT friend_id " +
                        "FROM friends " +
                        "WHERE user_id = ?) " +
                        "AND user_id " +
                        "IN(SELECT friend_id " +
                        "FROM friends " +
                        "WHERE user_id = ?)";

        return new ArrayList<>(jdbcTemplate.query(sqlQuery, this::getMapRowToUser, id, otherId));
    }

    private void getValidationUser(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User getMapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
