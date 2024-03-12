import Manager.Managers;
import Manager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.Task;
import Model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class InMemoryTaskManagerTest {

    private static Task firstTask = new Task("купить яблок", " ", TaskStatus.NEW);
    private static Task secondTask = new Task("", " ", TaskStatus.NEW);
    private static Epic firstEpic = new Epic("написать текст", " ");
    private static Subtask oneOneSubtask;
    private static Epic secondEpic = new Epic(" ", " ");
    private static Subtask towOneSubtask;
    private static Subtask towTowSubtask;
    private Managers manager = new Managers();
    private TaskManager taskManager = manager.getDefault();



    @Test
    void addNewTaskTest() {
        taskManager.addNewTask(firstTask);
        Assertions.assertNotNull(taskManager.getTaskById(firstTask.getId()));
        Assertions.assertEquals(taskManager.getTaskById(firstTask.getId()), firstTask);
    }

    @Test
    void addNewEpicTest() {
        taskManager.addNewEpic(secondEpic);
        Assertions.assertNotNull(taskManager.getEpicById(secondEpic.getId()));
        Assertions.assertEquals(taskManager.getEpicById(secondEpic.getId()), secondEpic);
    }


    @Test
    void addNewSubtaskTest() {
        taskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        Assertions.assertNotNull(taskManager.getSubtaskById(oneOneSubtask.getId()));
        Assertions.assertEquals(taskManager.getSubtaskById(oneOneSubtask.getId()).getName(), "начать");

    }

    @Test
    void deleteAllTasksTest() {
        taskManager.addNewTask(firstTask);
        taskManager.addNewTask(secondTask);
        taskManager.deleteAllTasks();
        Assertions.assertEquals(taskManager.getTasks().size(), 0);
    }

    @Test
    void deleteAllEpicsTest() {
        taskManager.addNewEpic(firstEpic);
        taskManager.addNewEpic(secondEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        towOneSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(towOneSubtask);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(towTowSubtask);

        taskManager.deleteAllEpics();
        Assertions.assertEquals(taskManager.getEpics().size(), 0);
        Assertions.assertEquals(taskManager.getSubtasks().size(), 0);
    }

    @Test
    void deleteAllSubtasksTest() {
        taskManager.addNewTask(firstTask);
        taskManager.addNewTask(secondTask);
        taskManager.addNewEpic(firstEpic);
        taskManager.addNewEpic(secondEpic);
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        towOneSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(towOneSubtask);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(towTowSubtask);

        taskManager.deleteAllSubtasks();
        Assertions.assertEquals(taskManager.getSubtasks().size(), 0);
        Assertions.assertEquals(taskManager.getAllSubtasks().size(), 0);
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
