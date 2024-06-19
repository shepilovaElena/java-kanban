import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.manager.FileBackedTaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class FileBackedTaskManagerLoadTest {

    public static Managers manager;
    public static FileBackedTaskManager fileBackedTaskManager;

    public static Task firstTask;
    public static Task secondTask;
    public static Epic firstEpic;
    public static Epic secondEpic;
    public static Subtask oneOneSubtask;

    @BeforeEach
    public void BeforeEach() throws IOException{
        File backFile = File.createTempFile("back-test", ".csv", new File("src/resources"));
        File backHistoryFile = File.createTempFile("back-history-test", ".csv", new File("src/resources"));
        manager = new Managers();
        fileBackedTaskManager = manager.getDefaultFileBackedTaskManager(backFile.toPath(), backHistoryFile.toPath());
        backFile.deleteOnExit();
        backHistoryFile.deleteOnExit();
    }


    @Test ///немножко дописать
    public void loadFromFileTest() throws IOException{
        fileBackedTaskManager.loadFromFile();
        Assertions.assertNotNull(fileBackedTaskManager.getEpics().size());
        Assertions.assertNotNull(fileBackedTaskManager.getTasks().size());
        Assertions.assertNotNull(fileBackedTaskManager.getSubtasks().size());

    }
    @Test
    public void loadFromFileHistoryTest() throws IOException {
        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW);
        fileBackedTaskManager.addNewTask(firstTask);
        secondTask = new Task("name", "description2", TaskStatus.NEW);
        fileBackedTaskManager.addNewTask(secondTask);
        firstEpic = new Epic("написать текст", "description3");
        fileBackedTaskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW);
        fileBackedTaskManager.addNewSubtask(oneOneSubtask);
        secondEpic = new Epic("name", "description5");
        fileBackedTaskManager.addNewEpic(secondEpic);

        fileBackedTaskManager.getTaskById(firstTask.getId());
        fileBackedTaskManager.getSubtaskById(oneOneSubtask.getId());

        List <Integer> checkHistory = new ArrayList<>();
        checkHistory.add(firstTask.getId());
        checkHistory.add(oneOneSubtask.getId());

        fileBackedTaskManager.loadFromFileHistory();
        Assertions.assertNotNull(fileBackedTaskManager.getHistory());
        Assertions.assertEquals(checkHistory.size(), fileBackedTaskManager.getHistory().size());

    }
}
