import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();

        Task firstTask = new Task("палучить посылку", " ");
        Task secondTask = new Task("приготовить обед", " ");
        Epic firstEpic = new Epic("дописать программу", " ");
        Subtask oneOneSubtask = new Subtask("написать тест", " ");
        Epic secondEpic = new Epic("прочитать книгу", " ");
        Subtask twoOneSubtask = new Subtask("прочитать главу 1", " ");
        Subtask twoTwoSubtask = new Subtask("прочитать главу 2", " ");

     TaskManager.addNewTask(firstTask, tasks);
     TaskManager.addNewTask(secondTask, tasks);
     TaskManager.addNewEpic(firstEpic, epics);
     TaskManager.addNewSubtask(firstEpic, oneOneSubtask);
     TaskManager.addNewEpic(secondEpic, epics);
     TaskManager.addNewSubtask(secondEpic, twoOneSubtask);
     TaskManager.addNewSubtask(secondEpic, twoTwoSubtask);

     TaskManager.getAllTasks(tasks, epics);

     TaskManager.changeTaskStatus(firstTask, TaskStatus.DONE);
     System.out.println(firstTask.getTaskStatus());
     TaskManager.changeSubtaskStatus(oneOneSubtask, TaskStatus.DONE, firstEpic);
     System.out.println(firstEpic.getTaskStatus());
     TaskManager.changeSubtaskStatus(twoOneSubtask, TaskStatus.DONE, secondEpic);
     System.out.println(secondEpic.getTaskStatus());

     System.out.println(oneOneSubtask.getId());
     System.out.println(firstEpic.getId());

     System.out.println(TaskManager.getSubtaskById(4, epics).getName());
     System.out.println(TaskManager.getSubtasksOfEpic(secondEpic).toString());

     TaskManager.deleteById(2, tasks, epics);
     TaskManager.getAllTasks(tasks, epics);


    }
}
