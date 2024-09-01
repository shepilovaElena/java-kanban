import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javakanban.manager.FileBackedTaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TypeOfTask;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;


import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManagerSaveTest {
    private static Managers manager;
    private static FileBackedTaskManager fileBackedTaskManager;

    private static Task firstTask;

    private static Task secondTask;
    private static Epic firstEpic;
    private static Subtask oneOneSubtask;
    private static Subtask twoOneSubtask;
    private static Epic secondEpic;
    String historyLine;

   


    @BeforeEach
    public void BeforeEach() throws IOException {
        File backFile = File.createTempFile("back-test", ".csv", new File("src/resources"));
        File backHistoryFile = File.createTempFile("back-history-test", ".csv", new File("src/resources"));
        manager = new Managers();
        fileBackedTaskManager = manager.getDefaultFileBackedTaskManager(backFile.toPath(), backHistoryFile.toPath());


        firstTask = new Task("купить яблок", "description1", TaskStatus.NEW, TypeOfTask.TASK,
                Duration.ofHours(1), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 13, 0));
        fileBackedTaskManager.addNewTask(firstTask);
        secondTask = new Task("отвезти кошку к ветеринару", "description2", TaskStatus.NEW,
                TypeOfTask.TASK, Duration.ofHours(4), LocalDateTime.of(2024, Month.SEPTEMBER, 15, 8, 0));
        fileBackedTaskManager.addNewTask(secondTask);
        firstEpic = new Epic("написать текст", "description3");
        fileBackedTaskManager.addNewEpic(firstEpic);
        oneOneSubtask = new Subtask("начать", "description4", firstEpic.getId(), TaskStatus.NEW,
                Duration.ofHours(2), LocalDateTime.of(2024, Month.SEPTEMBER, 1, 9, 0));
        fileBackedTaskManager.addNewSubtask(oneOneSubtask);
        secondEpic = new Epic("собрать вещи", "description5");
        fileBackedTaskManager.addNewEpic(secondEpic);
        twoOneSubtask = new Subtask("написать 1 стр.", "description6", firstEpic.getId(), TaskStatus.NEW,
                Duration.ofHours(3), LocalDateTime.of(2024, Month.SEPTEMBER, 2, 9, 0));
        fileBackedTaskManager.addNewSubtask(twoOneSubtask);

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
        lines.add("id,type,name,status,description,start-time,duration,end-time,epic");
        lines.add(firstTask.getId() + ",TASK" + "," + firstTask.getName() + "," + firstTask.getTaskStatus() + "," +
                firstTask.getDescription() + "," + "15.09.2024 13:00" + "," + "60" + "," + "15.09.2024 14:00" + ",");
        lines.add(secondTask.getId() + ",TASK" + "," + secondTask.getName() + "," + secondTask.getTaskStatus() + "," +
                secondTask.getDescription() + "," + "15.09.2024 08:00" + "," + "240" + "," + "15.09.2024 12:00" + ",");
        lines.add(firstEpic.getId() + ",EPIC" + "," + firstEpic.getName() + "," + firstEpic.getTaskStatus() + "," +
                firstEpic.getDescription() + "," + "01.09.2024 09:00" + "," + "1620" + "," + "02.09.2024 12:00" + ",");
        lines.add(secondEpic.getId() + ",EPIC" + "," + secondEpic.getName() + "," + secondEpic.getTaskStatus() + "," +
                secondEpic.getDescription() + "," + "," + "," + ",");
        lines.add(oneOneSubtask.getId() + ",SUBTASK" + "," + oneOneSubtask.getName() + "," +
                oneOneSubtask.getTaskStatus() + "," + oneOneSubtask.getDescription() + "," + "01.09.2024 09:00" + "," +
                "120" + "," + "01.09.2024 11:00" + "," + oneOneSubtask.getEpicId());
        lines.add(twoOneSubtask.getId() + ",SUBTASK" + "," + twoOneSubtask.getName() + "," +
                twoOneSubtask.getTaskStatus() + "," + twoOneSubtask.getDescription() + "," + "02.09.2024 09:00" + "," +
                "180" + "," + "02.09.2024 12:00" + "," + twoOneSubtask.getEpicId());


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

