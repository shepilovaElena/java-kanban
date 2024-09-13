import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.TaskStatus;


public class EpicStatusTest {
    public static Managers manager = new Managers();
    public static TaskManager taskManager = manager.getDefault();
    public static Epic firstEpic = new Epic("firstEpic", "new status");
    public static Epic secondEpic = new Epic("secondEpic", "done status");
    public static Epic thirdEpic = new Epic("thirdEpic", "new and done status");
    public static Epic fourthEpic = new Epic("fourthEpic", "in progress status");
    public static Subtask oneOneSubtask;
    public static Subtask twoOneSubtask;
    public static Subtask oneTwoSubtask;
    public static Subtask twoTwoSubtask;
    public static Subtask threeTwoSubtask;
    public static Subtask oneThreeSubtask;
    public static Subtask twoThreeSubtask;
    public static Subtask threeThreeSubtask;
    public static Subtask oneFourthSubtask;
    public static Subtask twoFourthSubtask;
    public static Subtask threeFourthSubtask;

    @Test
    void EpicStatusTest() {
        taskManager.addNewEpic(firstEpic);
        taskManager.addNewEpic(secondEpic);
        taskManager.addNewEpic(thirdEpic);
        taskManager.addNewEpic(fourthEpic);

        oneOneSubtask = new Subtask("oneOneSubtask", "check epic status", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(oneOneSubtask);
        twoOneSubtask = new Subtask("twoOneSubtask", "check epic status", firstEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(twoOneSubtask);

        oneTwoSubtask = new Subtask("oneTwoSubtask", "check epic status", secondEpic.getId(), TaskStatus.DONE);
        taskManager.addNewSubtask(oneTwoSubtask);
        twoTwoSubtask = new Subtask("twoTwoSubtask", "check epic status", secondEpic.getId(), TaskStatus.DONE);
        taskManager.addNewSubtask(twoTwoSubtask);
        threeTwoSubtask = new Subtask("threeTwoSubtask", "check epic status", secondEpic.getId(), TaskStatus.DONE);
        taskManager.addNewSubtask(threeTwoSubtask);

        oneThreeSubtask = new Subtask("oneThreeSubtask", "check epic status", thirdEpic.getId(), TaskStatus.DONE);
        taskManager.addNewSubtask(oneThreeSubtask);
        twoThreeSubtask = new Subtask("twoThreeSubtask", "check epic status", thirdEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(twoThreeSubtask);
        threeThreeSubtask = new Subtask("threeThreeSubtask", "check epic status", thirdEpic.getId(), TaskStatus.NEW);
        taskManager.addNewSubtask(threeThreeSubtask);

        oneFourthSubtask = new Subtask("oneFourthSubtask", "check epic status", fourthEpic.getId(), TaskStatus.DONE);
        taskManager.addNewSubtask(oneFourthSubtask);
        twoFourthSubtask = new Subtask("twoFourthSubtask", "check epic status", fourthEpic.getId(), TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(twoFourthSubtask);
        threeFourthSubtask = new Subtask("threeFourthSubtask", "check epic status", fourthEpic.getId(), TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(threeFourthSubtask);


        Assertions.assertEquals(firstEpic.getTaskStatus(), TaskStatus.NEW);
        Assertions.assertEquals(secondEpic.getTaskStatus(), TaskStatus.DONE);
        Assertions.assertEquals(thirdEpic.getTaskStatus(), TaskStatus.IN_PROGRESS);
        Assertions.assertEquals(fourthEpic.getTaskStatus(), TaskStatus.IN_PROGRESS);

    }
}
