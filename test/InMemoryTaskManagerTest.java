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


class InMemoryTaskManagerTest {

    private Task firstTask;
    private Task secondTask;
    private Epic firstEpic;
    private Subtask oneOneSubtask;
    private Epic secondEpic;
    private Subtask oneTwoSubtask;
    private Subtask twoTwoSubtask;
    private Subtask threeTwoSubtask;
    private Subtask fourTwoSubtask;
    private Subtask fifthTwoSubtask;
    private Subtask sixthTwoSubtask;

    private Task thirdTask;
    private Managers manager;

    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    public void BeforeEach(){

        manager = new Managers();
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        taskManager.addNewTask(firstTask);
        secondTask = new Task("отвезти кошку к ветеринару", "description2", TaskStatus.NEW, TypeOfTask.TASK, Duration.ofHours(4), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 8, 0));
        taskManager.addNewTask(secondTask);
        firstEpic = new Epic("написать текст", " ");
        taskManager.addNewEpic(firstEpic);
        secondEpic = new Epic(" ", " ");
        taskManager.addNewEpic(secondEpic);
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        taskManager.addNewSubtask(oneOneSubtask);
        oneTwoSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 2, 9, 0));
        taskManager.addNewSubtask(oneTwoSubtask);
        twoTwoSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofMinutes(75), LocalDateTime.of(2024, Month.SEPTEMBER, 2, 10, 45));
        taskManager.addNewSubtask(twoTwoSubtask);
        threeTwoSubtask = new Subtask(" ", "description3", secondEpic.getId(), TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.of(2024, Month.SEPTEMBER, 3, 10, 0));
        taskManager.addNewSubtask(threeTwoSubtask);
        fourTwoSubtask = new Subtask(" ", "description5", secondEpic.getId(), TaskStatus.NEW, Duration.ofMinutes(40), LocalDateTime.of(2024, Month.SEPTEMBER, 3, 10, 0));
        taskManager.addNewSubtask(fourTwoSubtask);
        fifthTwoSubtask = new Subtask(" ", "description6", secondEpic.getId(), TaskStatus.NEW, Duration.ofMinutes(40), LocalDateTime.of(2024, Month.SEPTEMBER, 3, 10, 0));
        taskManager.addNewSubtask(fifthTwoSubtask);
        sixthTwoSubtask = new Subtask(" ", "description6", secondEpic.getId(), TaskStatus.NEW, Duration.ofMinutes(20), LocalDateTime.of(2024, Month.SEPTEMBER, 3, 10, 0));
        taskManager.addNewSubtask(sixthTwoSubtask);
        thirdTask = new Task("собрать вещи", "description7", TaskStatus.IN_PROGRESS, TypeOfTask.TASK, Duration.ofHours(3), LocalDateTime.of(2024, Month.SEPTEMBER, 3, 10, 10));
    }

    @Test
    public void addNewTaskTestNotNull(){
        Assertions.assertNotNull(taskManager.getTaskById(firstTask.getId()));
    }

    @Test
    void addNewTaskTestEquals() {
        Assertions.assertEquals(taskManager.getTaskById(firstTask.getId()), firstTask);
    }

    @Test
    void addNewEpicTestNotNull() {
        Assertions.assertNotNull(taskManager.getEpicById(secondEpic.getId()));
    }

    @Test
    void addNewEpicTestEquals(){
        Assertions.assertEquals(taskManager.getEpicById(secondEpic.getId()), secondEpic);
    }


    @Test
    void addNewSubtaskTestNotNull() {
        Assertions.assertNotNull(taskManager.getSubtaskById(oneOneSubtask.getId()));
    }

    @Test
    void addNewSubtaskTestEquals() {
        Assertions.assertEquals(taskManager.getSubtaskById(oneOneSubtask.getId()).getName(), "начать");
    }

    @Test
    void deleteAllTasksTest() {
        taskManager.deleteAllTasks();
        List<Task> publicHistory = taskManager.getHistory();
        Assertions.assertEquals(0, publicHistory.size());
        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteAllEpicsTest() {
        List<Task> publicHistory = taskManager.getHistory();

        taskManager.getEpicById(firstEpic.getId());
        taskManager.getEpicById(secondEpic.getId());
        taskManager.deleteAllEpics();

        Assertions.assertEquals(0, taskManager.getEpics().size());
        Assertions.assertEquals(0, taskManager.getSubtasks().size());
        Assertions.assertEquals(0, publicHistory.size());
    }

    @Test
    void deleteAllSubtasksTest(){

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
    void updateTaskTest(){
        taskManager.updateTask(new Task("купить яблок", "красных", firstTask.getId(), TaskStatus.NEW, TypeOfTask.TASK));
        Assertions.assertEquals(taskManager.getTaskById(firstTask.getId()).getDescription(), "красных");
    }

    @Test
    void updateEpicTest() {
        taskManager.updateEpic(new Epic("придумать картинку", " ", secondEpic.getId()));
        Assertions.assertEquals(taskManager.getEpicById(secondEpic.getId()).getName(), "придумать картинку");
    }

    @Test
    void updateSubtaskTest() {
        taskManager.updateSubtask(new Subtask(" ", "description3_1", threeTwoSubtask.getId(), secondEpic.getId(), TaskStatus.NEW));
        Assertions.assertEquals("description3_1", taskManager.getSubtaskById(threeTwoSubtask.getId()).getDescription());
    }

    @Test
    void checkingIntersectionTwoTasksTest() {
        taskManager.addNewTask(thirdTask);
        Assertions.assertEquals(false, taskManager.getAllTasks().contains(thirdTask));
    }

}
