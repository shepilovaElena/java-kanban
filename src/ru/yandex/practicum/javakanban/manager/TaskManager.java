package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskManager<K extends Task> {
    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, Subtask> getSubtasks();

    List<Task> getHistory();

    // добавление новых задач
    int addNewTask(Task newTask);

    int addNewEpic(Epic newEpic);

    Optional<Integer> addNewSubtask(Subtask newSubtask);

    // удаление всех задач
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    // получение задачи по идентификатору
    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    // получение списка задач определенного эпика
    List<Subtask> getSubtasksOfEpic(int epicId);

    // получение списка всех задач
    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    // обновление задачи
    void updateTask(Task updateTask);

    void updateSubtask(Subtask updateSubtask);

    void updateEpic(Epic updateEpic);

    List<Task> getPrioritizedTasks();

}
