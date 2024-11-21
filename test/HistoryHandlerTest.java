import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.manager.*;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import ru.yandex.practicum.javakanban.handlers.LocalDateTimeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class HistoryHandlerTest {
    public Task firstTask;

    public Gson gson;

    public Managers manager;

    InMemoryTaskManager taskManager;

    public HistoryManager historyManager;
    public HttpTaskServer httpTaskServer;



    @BeforeEach
    public void beforeEach() throws IOException {

        manager = new Managers();
        taskManager = (InMemoryTaskManager) manager.getDefault();
        historyManager = manager.getDefaultHistory();

        httpTaskServer = new HttpTaskServer(historyManager);

        httpTaskServer.startServer();

        gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @AfterEach
    public void afterEach() {
        httpTaskServer.stopServer(httpTaskServer.getHttpServer());
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);
        taskManager.getTaskById(firstTask.getId());

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/history"))
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
