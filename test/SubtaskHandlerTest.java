import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.handlers.DurationAdapter;
import ru.yandex.practicum.javakanban.handlers.LocalDateTimeAdapter;
import ru.yandex.practicum.javakanban.handlers.SubtasksHandler;
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

public class SubtaskHandlerTest {

    public Subtask subtask;
    public Epic epic;

    public Gson gson;

    public Managers manager;

    public TaskManager taskManager;

    public HttpServer httpServer;



    @BeforeEach
    public void beforeEach() throws IOException {

        manager = new Managers();
        taskManager = manager.getDefault();

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager));


        httpServer.start();
        gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    @AfterEach
    public void afterEach() {
        httpServer.stop(0);
    }

    @Test
    public void getSubtasksTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);
        subtask = new Subtask("начать", "description4", epic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());



        HttpRequest httpRequestError = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/task/1"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseError = client.send(httpRequestError, handler);
        Assertions.assertEquals(404, responseError.statusCode());


        HttpRequest httpRequestWithId = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseWithId = client.send(httpRequestWithId, handler);
        Assertions.assertEquals(200, responseWithId.statusCode());
        Assertions.assertNotNull(responseWithId.body());
    }

    @Test
    public void postSubtasksTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);
        subtask = new Subtask("начать", "description4", epic.getId(), TaskStatus.NEW, Duration.ofHours(2),
                LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(subtask);
        String requestBody = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("http://localhost:8080/subtasks"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(taskManager.getSubtasks());


        Subtask updateSubtask = new Subtask("начать", "description", epic.getId(), TaskStatus.NEW, 2,
                Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        String requestBodyForUpdateTask = gson.toJson(updateSubtask);

        HttpRequest httpRequestForUpdateTask = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyForUpdateTask))
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> responseForUpdateTask = client.send(httpRequestForUpdateTask, handler);
        Assertions.assertEquals(201, responseForUpdateTask.statusCode());
        Assertions.assertEquals("description", taskManager.getSubtaskById(2).getDescription());
    }

    @Test
    public void deleteTasksTest() throws IOException, InterruptedException {
        epic = new Epic("написать текст", " ");
        taskManager.addNewEpic(epic);
        subtask = new Subtask("начать", "description4", epic.getId(), TaskStatus.NEW, Duration.ofHours(2),
                LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(subtask);
        Assertions.assertNotNull(taskManager.getSubtasks());

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(true, !taskManager.getSubtasks().containsKey(2));
    }
}
