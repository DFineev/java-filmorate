package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
public class User {
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

    private Set<Integer> friends;
}
