import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task firstTask = new Task("палучить посылку", " ", TaskStatus.NEW);
        taskManager.addNewTask(firstTask);
        Task secondTask = new Task("приготовить обед", " ", TaskStatus.NEW);
        taskManager.addNewTask(secondTask);

        Epic firstEpic = new Epic("дописать программу", " ");
        taskManager.addNewEpic(firstEpic);
        Subtask oneOneSubtask = new Subtask("написать тест", " ", 3,
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

        System.out.println(secondEpic.getTaskStatus());


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        System.out.println(oneOneSubtask.getId());
        System.out.println(firstEpic.getId());

        System.out.println(taskManager.getSubtaskById(twoTwoSubtask.getId()).getName());

        Task taskForUpdate = new Task("получить посылку", " ", TaskStatus.DONE, firstTask.getId());
        taskManager.updateTask(taskForUpdate);
        System.out.println(taskManager.getTasks().get(firstTask.getId()).getTaskStatus());

        Subtask subtaskForUpdate = new Subtask("прочитать главу 1", " ", secondEpic.getId(),
                                               TaskStatus.NEW, twoOneSubtask.getId());
        taskManager.updateSubtask(subtaskForUpdate);
        System.out.println(secondEpic.getTaskStatus());

        taskManager.deleteEpicById(2);
        System.out.println(taskManager.getAllTasks());


    }
}
