import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.javakanban.handlers.LocalDateTimeAdapter;
import ru.yandex.practicum.javakanban.manager.*;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import ru.yandex.practicum.javakanban.handlers.TasksHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class TaskHandlerTest {

    public Task firstTask;

    public Gson gson;

    public Managers manager;

    public TaskManager taskManager;

    public HttpServer httpServer;



    @BeforeEach
    public void beforeEach() throws  IOException {

        manager = new Managers();
        taskManager = manager.getDefault();

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/tasks", new TasksHandler(taskManager));

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
    public void getTasksTest() throws IOException, InterruptedException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());



        HttpRequest httpRequestError = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/task"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseError = client.send(httpRequestError, handler);
        Assertions.assertEquals(404, responseError.statusCode());


        HttpRequest httpRequestWithId = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> responseWithId = client.send(httpRequestWithId, handler);
        Assertions.assertEquals(200, responseWithId.statusCode());
        Assertions.assertNotNull(responseWithId.body());
    }

    @Test
    public void postTasksTest() throws IOException, InterruptedException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        String requestBody = gson.toJson(firstTask);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(taskManager.getTasks());

        Task updateFirstTask = new Task("купить красных яблок", "description1", TaskStatus.IN_PROGRESS, 1,
                TypeOfTask.TASK, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        String requestBodyForUpdateTask = gson.toJson(updateFirstTask);

        HttpRequest httpRequestForUpdateTask = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyForUpdateTask))
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> responseForUpdateTask = client.send(httpRequestForUpdateTask, handler);
        Assertions.assertEquals(201, responseForUpdateTask.statusCode());
        Assertions.assertEquals("купить красных яблок", taskManager.getTaskById(1).getName());
    }

    @Test
    public void deleteTasksTest() throws IOException, InterruptedException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);
        Assertions.assertNotNull(taskManager.getTasks());

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(httpRequest, handler);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(true, !taskManager.getTasks().containsKey(1));
    }
}
