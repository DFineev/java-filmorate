package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.adapter.DurationAdapter;
import ru.yandex.practicum.filmorate.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FilmorateApplicationTests {
    private static final int PORT = 8080;

    public Gson gson = getGson();

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        return gsonBuilder.create();
    }

    @Test
    void shouldReturnFilmNameNullOrEmptyException() throws IOException, InterruptedException {
        final Film filmWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(90)
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String requestBody = gson.toJson(filmWithEmptyName);
        HttpRequest request = requestBuilder
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDescriptionTooLongException() throws IOException, InterruptedException {
        final Film filmWithTooLongDescription = Film.builder().id(1)
                .name("filmName")
                .description(generateString(201))
                .duration(90)
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String requestBody = gson.toJson(filmWithTooLongDescription);
        HttpRequest request = requestBuilder
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmReleaseDateException() throws IOException, InterruptedException {

        final Film filmWithTooOldReleaseDate = Film.builder().id(1)
                .name("filmName")
                .description("Test Description")
                .duration(90)
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = gson.toJson(filmWithTooOldReleaseDate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDurationIsNegativeValueException() throws IOException, InterruptedException {

        final Film filmWithNegativeDuration = Film.builder().id(1)
                .name("filmName")
                .description("Test Description")
                .duration(-90)
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = gson.toJson(filmWithNegativeDuration);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    public String generateString(int stringLength) {

        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        StringBuilder buffer = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        System.out.println(generatedString);
        return generatedString;
    }

    @Test
    public void shouldReturnUserEmailNullOrEmptyException() throws IOException, InterruptedException {
        final User userWithEmptyEmail = User.builder().id(1)
                .email("")
                .login("userLogin")
                .name("userName")
                .birthday(LocalDate.of(1997, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        String requestBody = gson.toJson(userWithEmptyEmail);
        HttpRequest request = requestBuilder
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());

        final User userWithNullEmail = User.builder().id(1)
                .login("userLogin")
                .name("userName")
                .birthday(LocalDate.of(1997, 1, 23))
                .build();

        client = HttpClient.newHttpClient();
        uri = URI.create("http://localhost:" + PORT + "/users");
        requestBuilder = HttpRequest.newBuilder();
        requestBody = gson.toJson(userWithNullEmail);
        request = requestBuilder
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(request, handler);
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnUserEmailIsIncorrectException() throws IOException, InterruptedException {
        final User userWithIncorrectEmail = User.builder()
                .id(1)
                .email("Test.mail")
                .login("userLogin")
                .name("userName")
                .birthday(LocalDate.of(1997, 1, 23))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = gson.toJson(userWithIncorrectEmail);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnUserLoginIsEmptyOrBlankException() throws IOException, InterruptedException {
        final User userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1997, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = gson.toJson(userWithEmptyLogin);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }
    @Test
    public void shouldReturnUserLoginContainsBlankSpaceException() throws IOException, InterruptedException {
        final User userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1997, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = gson.toJson(userWithBlankInLogin);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }
    @Test
    public void shouldReturnUserBirthdayException() throws IOException, InterruptedException {
        final User userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100, 2, 28))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/users");
        String requestBody = gson.toJson(userWithBirthdayInTheFuture);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }
}
