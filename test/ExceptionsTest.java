import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.manager.TypeOfTask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class ExceptionsTest {

private static Managers manager;
private static TaskManager taskManager;
private static HistoryManager historyManager;
private static File backHistoryFile;
private static File backFile;

private static Task firstTask;

    @BeforeEach
    public void BeforeEach() throws IOException {

        backFile = File.createTempFile("back-test", ".csv", new File("src/resources"));
        backHistoryFile = File.createTempFile("back-history-test", ".csv", new File("src/resources"));
        manager = new Managers();
        taskManager = manager.getDefaultFileBackedTaskManager(backFile.toPath(), backHistoryFile.toPath());
        historyManager = manager.getDefaultHistory();
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));

        backFile.deleteOnExit();
        backHistoryFile.deleteOnExit();
    }

    @Test
    public void Exception1Test() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            taskManager.addNewTask(null);
            }, "Ошибка записи в файл.");
    }

    @Test
    public void Exception2Test() {
        Assertions.assertDoesNotThrow(() -> {
            taskManager.addNewTask(firstTask);
        });
    }
}
