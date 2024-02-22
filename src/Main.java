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
        Subtask oneOneSubtask = new Subtask("написать тест", " ", firstEpic.getId(),
                                            TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(firstEpic, oneOneSubtask);
        Epic secondEpic = new Epic("прочитать книгу", " ");
        taskManager.addNewEpic(secondEpic);
        Subtask twoOneSubtask = new Subtask("прочитать главу 1", " ", secondEpic.getId(),
                                            TaskStatus.IN_PROGRESS);
        taskManager.addNewSubtask(secondEpic, twoOneSubtask);
        Subtask twoTwoSubtask = new Subtask("прочитать главу 2", " ", secondEpic.getId(),
                                            TaskStatus.IN_PROGRESS);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());


        System.out.println(oneOneSubtask.getId());
        System.out.println(firstEpic.getId());

        System.out.println(taskManager.getSubtaskById(4).getName());

        Task taskForUpdate = new Task("получить посылку", " ", TaskStatus.DONE, firstTask.getId());
        taskManager.updateTask(taskForUpdate);
        System.out.println(taskManager.tasks.get(firstTask.getId()).getTaskStatus());

        Subtask subtaskForUpdate = new Subtask("прочитать главу 1", " ", secondEpic.getId(),
                                               TaskStatus.DONE, twoTwoSubtask.getId());
        taskManager.updateSubtask(subtaskForUpdate);
        System.out.println(secondEpic.getTaskStatus());

        taskManager.deleteById(2);
        System.out.println(taskManager.getAllTasks());


    }
}
