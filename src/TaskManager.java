import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager<K extends Task> {
    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, Subtask> getSubtasks();

    // добавление новых задач
    int addNewTask(Task newTask);

    int addNewEpic(Epic newEpic);

    int addNewSubtask(Subtask newSubtask);

    // удаление всех задач
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    // получение задачи по идентификатору
    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    // получение списка задач определенного эпика
    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    // получение списка всех задач
    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    // обновление задачи
    void updateTask(Task updateTask);

    void updateSubtask(Subtask updateSubtask);

    void updateEpic(Epic updateEpic);


}
