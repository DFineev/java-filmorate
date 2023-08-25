package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.util.FilmReleaseDate;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
   @FilmReleaseDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Integer> likes;
    @NotNull
    private Mpa mpa;
    private Set<Genre> genres;

}
