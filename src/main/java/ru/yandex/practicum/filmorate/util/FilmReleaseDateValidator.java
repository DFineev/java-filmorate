package ru.yandex.practicum.filmorate.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmReleaseDateValidator implements ConstraintValidator<FilmReleaseDate, LocalDate> {

    private static final LocalDate THE_OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate dateToCheck, ConstraintValidatorContext constraintValidatorContext) {
        return dateToCheck.isAfter(THE_OLDEST_RELEASE_DATE);
    }
}

