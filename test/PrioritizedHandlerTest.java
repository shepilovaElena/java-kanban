import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.handlers.LocalDateTimeAdapter;
import ru.yandex.practicum.javakanban.handlers.PrioritizedHandler;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.InMemoryTaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TypeOfTask;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
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

public class PrioritizedHandlerTest {
    public Task firstTask;
    public Epic firstEpic;
    public Subtask oneOneSubtask;

    public Gson gson;

    public Managers manager;

    InMemoryTaskManager taskManager;

    public HistoryManager historyManager;
    public HttpServer httpServer;



    @BeforeEach
    public void beforeEach() throws IOException {

        manager = new Managers();
        taskManager = (InMemoryTaskManager) manager.getDefault();
        historyManager = manager.getDefaultHistory();

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));

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
    public void getPrioritizedTest() throws IOException, InterruptedException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);
        taskManager.getTaskById(firstTask.getId());
        firstEpic = new Epic("написать текст", " ");
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(oneOneSubtask);
        taskManager.getSubtaskById(oneOneSubtask.getId());

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/prioritized"))
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
    }
}
