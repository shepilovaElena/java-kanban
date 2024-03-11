import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class ManagersTest {
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
        firstTask = new Task("купить яблок", " ", TaskStatus.NEW);
        secondTask = new Task("", " ", TaskStatus.NEW);
        firstEpic = new Epic("написать текст", " ");
        oneOneSubtask = new Subtask("начать", " ", firstEpic.getId(), TaskStatus.NEW);
        secondEpic = new Epic("", " ");
        towOneSubtask = new Subtask("ij", " ", secondEpic.getId(), TaskStatus.NEW);
        towTowSubtask = new Subtask("", " ", secondEpic.getId(), TaskStatus.NEW);
        taskManager = manager.getDefault();
        historyManager = manager.getDefaultHistory();
    }


    @Test
    public void getDefaultTest() {
        Assertions.assertNotNull(taskManager);
    }

    @Test
    public void getDefaultHistoryTest() {
        Assertions.assertNotNull(historyManager);
    }

}

