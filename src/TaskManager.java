import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public static int id = 0;

    // генерация id
    public static int generateId() {
        id++;
        return id;
    }

    // добавление новых задач
    public static void addNewTask(Task newTask, HashMap<Integer, Task> tasks) {
        newTask.setId(generateId());
        tasks.put(newTask.getId(), newTask);
    }

    public static void addNewEpic(Epic newEpic, HashMap<Integer, Epic> epics) {
        newEpic.setId(generateId());
        epics.put(newEpic.getId(), newEpic);
    }

    public static void addNewSubtask(Epic epic, Subtask newSubtask) {
        newSubtask.setId(generateId());
        epic.epicSubtasks.add(newSubtask);
    }

    // изменение статуса задачи
    public static void changeTaskStatus(Task task, TaskStatus taskStatus) {
        task.setTaskStatus(taskStatus);
    }

    public static void changeSubtaskStatus(Subtask subtask, TaskStatus taskStatus, Epic epic) {
        subtask.setTaskStatus(taskStatus);

        for (Subtask epicSubtask : epic.epicSubtasks) {

            if (!(epicSubtask.getTaskStatus() == TaskStatus.DONE)) {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            } else {
                epic.setTaskStatus(TaskStatus.DONE);
            }
        }
    }


    // удаление всех задач
    public static void deleteAllTasks(HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics){
        tasks.clear();
        epics.clear();
    }

    // получение задачи по идентификатору
    public static Task getTaskById(int id, HashMap<Integer, Task> tasks) {
        return tasks.get(id) ;
    }

    public static Epic getEpicById(int id, HashMap<Integer, Epic> epics) {
        return  epics.get(id);
    }

    public static Subtask getSubtaskById(int id, HashMap<Integer, Epic> epics) {
        int epicId = 0;
        int subtaskIndex = 0;

        for (Epic epic : epics.values()){
            for (int i = 0; i < epic.epicSubtasks.size(); i++) {
                if(epic.epicSubtasks.get(i).getId() == id){
                    epicId = epic.getId();
                    subtaskIndex = i;
                }
            }
        }
        return epics.get(epicId).epicSubtasks.get(subtaskIndex);
    }

    // получение списка задач определенного эпика
    public static ArrayList<Subtask> getSubtasksOfEpic(Epic epic) {
        return epic.epicSubtasks;
    }



    // получение списка всех задач
    public static void getAllTasks(HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics) {
     for (Task task : tasks.values()) {
         System.out.println(task.toString());
     }
     for (Epic epic : epics.values()) {
         System.out.println(epic.toString());

             for (Subtask subtask : epic.epicSubtasks) {
                 System.out.println(subtask);

         }
     }
    }

    // удаление по id
    public static void deleteById(int id, HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics){
        if(tasks.containsKey(id)) {
        tasks.remove(id);
        return;
        }
        for (Epic epic : epics.values()){
                if(epic.getEpicSubtasks().contains(getTaskById(id, tasks))){
                    epic.getEpicSubtasks().remove(getTaskById(id, tasks));
                return;
            }
        }

    }

    // обновление задачи
    public static void updateTask(Task updateTask, HashMap<Integer, Task> tasks){
        tasks.put(updateTask.getId(), updateTask);
    }

    public static void updateSubtask(Subtask updateSubtask, Epic subtasksEpic){
              for (int i = 0; i < subtasksEpic.epicSubtasks.size(); i++){
                  if(subtasksEpic.epicSubtasks.get(i).getId() == updateSubtask.getId()){
                      subtasksEpic.epicSubtasks.add(i,updateSubtask);
              }

          }
    }

    public static void updateEpic(Epic updateEpic, HashMap<Integer, Epic> epics){
        epics.put(updateEpic.getId(), updateEpic);
    }
}




