package ru.yandex.practicum.filmorate.exception;

public class ValidException extends RuntimeException {
    private final String parameter;

    public ValidException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
