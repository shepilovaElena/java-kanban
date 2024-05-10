import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.manager.*;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManagerSaveTest {
    private static Managers manager;
    private static FileBackedTaskManager fileBackedTaskManager;

    private static Task firstTask;

    private static Task secondTask;
    private static Epic firstEpic;
    private static Subtask oneOneSubtask;
    private static Epic secondEpic;
    String historyLine;




    @BeforeEach
    public void BeforeEach() throws IOException {
        File backFile = File.createTempFile("back-test", ".csv", new File("src/resources"));
        File backHistoryFile = File.createTempFile("back-history-test", ".csv", new File("src/resources"));
        manager = new Managers();
        fileBackedTaskManager = manager.getDefaultFileBackedTaskManager(backFile.toPath(), backHistoryFile.toPath());
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
        backFile.deleteOnExit();
        backHistoryFile.deleteOnExit();
    }


    @Test
    public void saveTest() throws IOException {
        List<String> linesOfFile = new ArrayList<>();
        FileReader reader = new FileReader(fileBackedTaskManager.getFile().toFile());
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            linesOfFile.add(line);
        }
        br.close();

        List<String> lines = new ArrayList<>();
        lines.add("id,type,name,status,description,epic");
        lines.add(firstTask.getId() + ",TASK" + "," + firstTask.getName() + "," + firstTask.getTaskStatus() + "," +
                firstTask.getDescription() + ",");
        lines.add(secondTask.getId() + ",TASK" + "," + secondTask.getName() + "," + secondTask.getTaskStatus() + "," +
                secondTask.getDescription() + ",");
        lines.add(firstEpic.getId() + ",EPIC" + "," + firstEpic.getName() + "," + firstEpic.getTaskStatus() + "," +
                firstEpic.getDescription() + ",");
        lines.add(secondEpic.getId() + ",EPIC" + "," + secondEpic.getName() + "," + secondEpic.getTaskStatus() + "," +
                secondEpic.getDescription() + ",");
        lines.add(oneOneSubtask.getId() + ",SUBTASK" + "," + oneOneSubtask.getName() + "," + oneOneSubtask.getTaskStatus() +
                "," + oneOneSubtask.getDescription() + "," + oneOneSubtask.getEpicId());

        Assertions.assertEquals(lines, linesOfFile);
    }



    @Test
    public void saveHistoryTest() throws IOException {
        fileBackedTaskManager.getTaskById(firstTask.getId());
        fileBackedTaskManager.getSubtaskById(oneOneSubtask.getId());
        fileBackedTaskManager.getEpicById(firstEpic.getId());
        fileBackedTaskManager.deleteAllTasks();

        BufferedReader br = new BufferedReader(new FileReader(fileBackedTaskManager.getFileToHistory().toFile()));
        while (br.ready()) {
            historyLine = br.readLine();
        }
        br.close();

        String checkHistoryLine = oneOneSubtask.getId() + "," + firstEpic.getId() + ",";

        Assertions.assertNotNull(historyLine);
        Assertions.assertEquals(checkHistoryLine, historyLine);

    }
}

