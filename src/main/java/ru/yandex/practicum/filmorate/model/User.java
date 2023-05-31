package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Builder
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    @NotNull
    @NotBlank
    @Email
    private final String email;
    @NotNull
    @NotBlank
    private final String login;
    private String name;
    @PastOrPresent
    private final LocalDate birthday;
}
