import Magager.HistoryManager;
import Magager.Managers;
import Magager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.Task;
import Model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void beforeAll() {
        manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();

        firstTask = new Task("купить яблок", " ", TaskStatus.NEW);
        taskManager.addNewTask(firstTask);
        secondTask = new Task("", " ", TaskStatus.NEW);
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
        void getHistoryTest() {
        taskManager.getTaskById(firstTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        List<Task> history = taskManager.getHistoryManager().getHistory();
        List<Task> history1 = new ArrayList<>();
        history1.add(firstTask);
        history1.add(firstEpic);
        Assertions.assertNotNull(history);
        Assertions.assertNotNull(history1);
        Assertions.assertEquals(history1, history);
    }

    @Test
    void add() {
    }
}
