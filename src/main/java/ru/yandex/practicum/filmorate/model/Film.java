package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotNull(message = "Название фильма не должно быть пустым")
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

}
