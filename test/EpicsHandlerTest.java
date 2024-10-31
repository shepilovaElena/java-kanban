import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.handlers.EpicsHandler;
import ru.yandex.practicum.javakanban.handlers.LocalDateTimeAdapter;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.TaskStatus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class EpicsHandlerTest {

    public Epic epic;
    public Subtask subtask;

    public Gson gson;

    public Managers manager;

    public TaskManager taskManager;

    public HttpServer httpServer;



    @BeforeEach
    public void beforeEach() throws IOException {

        manager = new Managers();
        taskManager = manager.getDefault();

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/epics", new EpicsHandler(taskManager));

        httpServer.start();
        gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @AfterEach
    public void afterEach() {
        httpServer.stop(0);
    }

    @Test
    public void getEpicsTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);
        subtask = new Subtask("начать", "description4", epic.getId(), TaskStatus.NEW, Duration.ofHours(2),
                LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/epics"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());



        HttpRequest httpRequestError = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/epi/1"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseError = client.send(httpRequestError, handler);
        Assertions.assertEquals(404, responseError.statusCode());


        HttpRequest httpRequestWithId = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseWithId = client.send(httpRequestWithId, handler);
        Assertions.assertEquals(200, responseWithId.statusCode());
        Assertions.assertNotNull(responseWithId.body());
    }

    @Test
    public void postEpicsTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);
        subtask = new Subtask("начать", "description4", epic.getId(), TaskStatus.NEW, Duration.ofHours(2),
                LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(subtask);
        String requestBody = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("http://localhost:8080/epics"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(taskManager.getEpics());


    }

    @Test
    public void deleteTasksTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);

        Assertions.assertNotNull(taskManager.getEpics());

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(true, !taskManager.getEpics().containsKey(1));
    }
}
