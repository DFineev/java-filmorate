package ru.yandex.practicum.filmorate.storage.DAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public Mpa getMpaById(int mpaId) {
        String sqlQuery =
                "SELECT rating_mpa_id, name " +
                        "FROM mpa_type " +
                        "WHERE rating_mpa_id=?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, mpaId);
    }

    public List<Mpa> findAll() {
        List<Mpa> mpaList = new ArrayList<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet(
                "SELECT rating_mpa_id, name " +
                        "FROM mpa_type");

        while (mpaRows.next()) {
            Mpa mpa = Mpa.builder()
                    .id(mpaRows.getInt("rating_mpa_id"))
                    .name(mpaRows.getString("name"))
                    .build();
            mpaList.add(mpa);
        }
        return mpaList;
    }

    public void addMpaToFilm(Film film) {
        findAll().forEach(mpa -> {
            if (Objects.equals(film.getMpa().getId(), mpa.getId())) {
                film.setMpa(mpa);
            }
        });
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("rating_mpa_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
