import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.manager.TypeOfTask;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


class ManagersTest {
    private static Managers manager;
    private static Task firstTask;
    private static Task secondTask;
    private static Epic firstEpic;
    private static Subtask oneOneSubtask;
    private static Epic secondEpic;
    private static Subtask towOneSubtask;

    private static Subtask towTowSubtask;
    private static TaskManager taskManager;

    private static HistoryManager historyManager;

    @BeforeAll
    public static void beforeAll() {
        manager = new Managers();
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        secondTask = new Task("отвезти кошку к ветеринару", "description2", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(4), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 8, 0));
        firstEpic = new Epic("написать текст", " ");
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        secondEpic = new Epic(" ", " ");
        towOneSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        towTowSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();
    }


    @Test
    public void getDefaultTest() {
        Assertions.assertNotNull(taskManager);
    }

    @Test
    public void getDefaultHistoryTest() {
        Assertions.assertNotNull(historyManager);
    }

}

