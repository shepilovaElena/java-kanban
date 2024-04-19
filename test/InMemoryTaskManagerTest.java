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


class InMemoryTaskManagerTest {

    private static Task firstTask;
    private static Task secondTask;
    private static Epic firstEpic;
    private static Subtask oneOneSubtask;
    private static Epic secondEpic;
    private static Subtask towOneSubtask;
    private static Subtask towTowSubtask;
    private Managers manager;
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    public void BeforeEach() {
        firstTask = new Task("купить яблок", " ", TaskStatus.NEW);
        secondTask = new Task("", " ", TaskStatus.NEW);
        firstEpic = new Epic("написать текст", " ");
        secondEpic = new Epic(" ", " ");
        towOneSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);

        manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();
    }

    @Test
    void addNewTaskTestNotNull() {
        taskManager.addNewTask(firstTask);
        Assertions.assertNotNull(taskManager.getTaskById(firstTask.getId()));
    }

    @Test
    void addNewTaskTestEquals() {
        taskManager.addNewTask(firstTask);
        Assertions.assertEquals(taskManager.getTaskById(firstTask.getId()), firstTask);
    }

    @Test
    void addNewEpicTestNotNull() {
        taskManager.addNewEpic(secondEpic);
        Assertions.assertNotNull(taskManager.getEpicById(secondEpic.getId()));
    }

    @Test
    void addNewEpicTestEquals() {
        taskManager.addNewEpic(secondEpic);
        taskManager.addNewEpic(firstEpic);
        taskManager.addNewTask(secondTask);
        Assertions.assertEquals(taskManager.getEpicById(secondEpic.getId()), secondEpic);
    }


    @Test
    void addNewSubtaskTestNotNull() {
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        Assertions.assertNotNull(taskManager.getSubtaskById(oneOneSubtask.getId()));
    }

    @Test
    void addNewSubtaskTestEquals() {
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        Assertions.assertEquals(taskManager.getSubtaskById(oneOneSubtask.getId()).getName(), "начать");
    }

    @Test
    void deleteAllTasksTest() {
        taskManager.addNewTask(firstTask);
        taskManager.addNewTask(secondTask);

        taskManager.deleteAllTasks();

        List<Task> publicHistory = taskManager.getHistory();
        Assertions.assertEquals(0, publicHistory.size());
        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteAllEpicsTest() {
        taskManager.addNewEpic(firstEpic);
        taskManager.addNewEpic(secondEpic);
        towOneSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        taskManager.addNewSubtask(towOneSubtask);
        taskManager.addNewSubtask(towTowSubtask);
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getEpicById(secondEpic.getId());

        taskManager.deleteAllEpics();

        List<Task> publicHistory = taskManager.getHistory();

        Assertions.assertEquals(0, taskManager.getEpics().size());
        Assertions.assertEquals(0, taskManager.getSubtasks().size());
        Assertions.assertEquals(0, publicHistory.size());
    }

    @Test
    void deleteAllSubtasksTest() {
        taskManager.addNewTask(firstTask);
        taskManager.addNewTask(secondTask);
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewEpic(secondEpic);
        towOneSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        taskManager.addNewSubtask(towOneSubtask);
        taskManager.addNewSubtask(towTowSubtask);
        taskManager.getTaskById(firstTask.getId());
        taskManager.getTaskById(secondTask.getId());
        taskManager.getEpicById(firstEpic.getId());
        taskManager.getSubtaskById(oneOneSubtask.getId());

        taskManager.deleteAllSubtasks();

        List<Task> publicHistory = taskManager.getHistory();
        List<Task> checkHistory = new ArrayList<>();
        checkHistory.add(firstTask);
        checkHistory.add(secondTask);
        checkHistory.add(firstEpic);

        Assertions.assertEquals(0, taskManager.getSubtasks().size());
        Assertions.assertEquals(0, taskManager.getAllSubtasks().size());
        Assertions.assertEquals(checkHistory, publicHistory);
    }

    @Test
    void updateTaskTest() {
        taskManager.addNewTask(firstTask);
        taskManager.updateTask(new Task("купить яблок", "красных", TaskStatus.NEW, firstTask.getId()));
        Assertions.assertEquals(taskManager.getTaskById(firstTask.getId()).getDescription(), "красных");
    }

    @Test
    void updateEpicTest() {
        taskManager.addNewEpic(secondEpic);
        Epic newEpic = new Epic("придумать картинку", " ", secondEpic.getId());
        taskManager.updateEpic(newEpic);
        Assertions.assertEquals(taskManager.getEpicById(secondEpic.getId()).getName(), "придумать картинку");
    }

    @Test
    void updateSubtaskTest() {
        taskManager.addNewEpic(secondEpic);
        towTowSubtask = new Subtask(" ", " ", secondEpic.getId(), TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(towTowSubtask);
        Assertions.assertEquals(taskManager.getSubtaskById(towTowSubtask.getId()).getTaskStatus(), TaskStatus.IN_PROGRESS);
    }
}
