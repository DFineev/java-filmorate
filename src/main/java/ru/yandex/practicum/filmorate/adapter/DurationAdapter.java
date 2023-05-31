package ru.yandex.practicum.filmorate.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        String value;
        if (duration != null) {
            value = duration.toString();
            jsonWriter.value(value);
        } else {
            jsonWriter.nullValue();
        }

    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return Duration.parse(value);
    }
}
