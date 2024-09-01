import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.manager.TypeOfTask;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


class InMemoryHistoryTaskManagerTest {

    private static Managers manager;
    private static Task firstTask;
    private static Task secondTask;
    private static Epic firstEpic;
    private static Subtask oneOneSubtask;
    private static Epic secondEpic;
    private static Subtask oneTwoSubtask;

    private static Subtask towTowSubtask;
    private static TaskManager taskManager;
    private static HistoryManager historyManager;



    @BeforeEach
    public void beforeEach() {
        manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();



        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);
        secondTask = new Task("отвезти кошку к ветеринару", "description2", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(3), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 8, 0));
        taskManager.addNewTask(secondTask);
        firstEpic = new Epic("написать текст", " ");
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 5, 9, 0));
        taskManager.addNewSubtask(oneOneSubtask);
        secondEpic = new Epic("", " ");
        taskManager.addNewEpic(secondEpic);
        oneTwoSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        taskManager.addNewSubtask(oneTwoSubtask);
        towTowSubtask = new Subtask(" ", "description5", secondEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 12, 0));
        taskManager.addNewSubtask(towTowSubtask);
   }


    @Test
        void getHistoryListTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getTaskById(secondTask.getId());
        taskManager.getTaskById(firstTask.getId());
        List<Task> publicHistory = taskManager.getHistory();
        List<Task> history1 = new ArrayList<>();
        history1.add(firstEpic);
        history1.add(secondTask);
        history1.add(firstTask);
        Assertions.assertNotNull(publicHistory);
        Assertions.assertNotNull(history1);
        Assertions.assertEquals(history1, publicHistory);
    }

    @Test
       void addNewTaskInHistoryTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getTaskById(secondTask.getId());
        List<Task> publicHistory = taskManager.getHistory();
        List<Task> history1 = new ArrayList<>();
        history1.add(firstTask);
        history1.add(firstEpic);
        history1.add(secondTask);
        Assertions.assertNotNull(publicHistory);
        Assertions.assertNotNull(history1);
        Assertions.assertEquals(history1, publicHistory);
    }

    @Test
    void addOldTaskInHistoryTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getTaskById(secondTask.getId());
        taskManager.getTaskById(firstTask.getId());
        List<Task> publicHistory = taskManager.getHistory();
        List<Task> history2 = new ArrayList<>();
        history2.add(firstEpic);
        history2.add(secondTask);
        history2.add(firstTask);

        Assertions.assertNotNull(publicHistory);
        Assertions.assertNotNull(history2);
        Assertions.assertEquals(history2, publicHistory);
    }

    @Test
    void removeTaskFromHistoryTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getTaskById(secondTask.getId());
        taskManager.getEpicById(secondEpic.getId());
        taskManager.deleteEpicById(secondEpic.getId());
        taskManager.deleteTaskById(firstTask.getId());
        taskManager.getSubtaskById(oneTwoSubtask.getId());
        taskManager.deleteSubtaskById(oneTwoSubtask.getId());
        List<Task> publicHistory = taskManager.getHistory();
        List<Task> history2 = new ArrayList<>();
        history2.add(firstEpic);
        history2.add(secondTask);
        Assertions.assertNotNull(publicHistory);
        Assertions.assertNotNull(history2);
        Assertions.assertEquals(history2, publicHistory);
    }

    @Test
    void removeAllTasksFromHistoryTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getTaskById(secondTask.getId());
        taskManager.getEpicById(secondEpic.getId());
        taskManager.deleteAllEpics();
        taskManager.deleteAllTasks();
        List<Task> publicHistory = taskManager.getHistory();
        List<Task> history2 = new ArrayList<>();

        Assertions.assertEquals(history2, publicHistory);
    }
}
