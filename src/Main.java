import ru.yandex.practicum.javakanban.manager.ManagerSaveException;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException, ManagerSaveException {

        Managers manager = new Managers();
        TaskManager taskManager = manager.getDefault();

        Task firstTask = new Task("палучить посылку", " ", TaskStatus.NEW);
        taskManager.addNewTask(firstTask);
        Task secondTask = new Task("приготовить обед", " ", TaskStatus.NEW);
        taskManager.addNewTask(secondTask);

        Epic firstEpic = new Epic("дописать программу", " ");
        taskManager.addNewEpic(firstEpic);
        Subtask oneOneSubtask = new Subtask("написать тест", " ", firstEpic.getId(),
                                            TaskStatus.DONE);
        taskManager.addNewSubtask(oneOneSubtask);


        Epic secondEpic = new Epic("прочитать книгу", " ");
        taskManager.addNewEpic(secondEpic);

        Subtask twoOneSubtask = new Subtask("прочитать главу 1", " ", secondEpic.getId(),
                                            TaskStatus.DONE);
        taskManager.addNewSubtask(twoOneSubtask);

        Subtask twoTwoSubtask = new Subtask("прочитать главу 2", " ", secondEpic.getId(),
                                            TaskStatus.DONE);
        taskManager.addNewSubtask(twoTwoSubtask);
        System.out.println(taskManager.getSubtaskById(oneOneSubtask.getId()).getId());

        System.out.println(secondEpic.getTaskStatus());


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        System.out.println(oneOneSubtask.getId());
        System.out.println(firstEpic.getId());

        System.out.println(taskManager.getSubtaskById(twoTwoSubtask.getId()).getName());

        Task taskForUpdate = new Task("получить посылку", " ", TaskStatus.DONE, firstTask.getId());
        taskManager.updateTask(taskForUpdate);


        Subtask subtaskForUpdate = new Subtask("прочитать главу 1", " ", secondEpic.getId(),
                                               TaskStatus.NEW, twoOneSubtask.getId());
        taskManager.updateSubtask(subtaskForUpdate);
        System.out.println(secondEpic.getTaskStatus());

        taskManager.deleteEpicById(2);
        System.out.println(taskManager.getAllTasks());

        taskManager.deleteEpicById(3);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getEpicById(3));


    }
}
