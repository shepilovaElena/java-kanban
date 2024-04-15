import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class InMemoryHistoryTaskManagerTest {

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



    @BeforeEach
    public void beforeEach() {
        manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();



        firstTask = new Task("купить яблок", " ", TaskStatus.NEW);
        taskManager.addNewTask(firstTask);
        secondTask = new Task(" ", " ", TaskStatus.NEW);
        taskManager.addNewTask(secondTask);
        firstEpic = new Epic("написать текст", " ");
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        secondEpic = new Epic("", " ");
        taskManager.addNewEpic(secondEpic);
        towOneSubtask = new Subtask("ij", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(towOneSubtask);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
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
}
