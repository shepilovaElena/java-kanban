

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();



    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    // генерация id
    private int generateId() {
        id++;
        return id;
    }

    // добавление новых задач
    public int addNewTask(Task newTask) {
        newTask.setId(generateId());
        tasks.put(newTask.getId(), newTask);
        return newTask.getId();
    }

    public int addNewEpic(Epic newEpic) {
        newEpic.setId(generateId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic.getId();
    }

    public int addNewSubtask(Subtask newSubtask) {
        if (epics.containsKey(newSubtask.getEpicId())) {
            int newSubtaskId = generateId();
            newSubtask.setId(newSubtaskId);

            Epic epic = epics.get(newSubtask.getEpicId());
            epic.addNewSubtask(newSubtask.getId());

            subtasks.put(newSubtask.getId(), newSubtask);
            changeEpicStatus(epics.get(newSubtask.getEpicId()));
        }
        return newSubtask.getId();
    }

    // изменение статуса задачи

    private void changeEpicStatus(Epic epic) {

            if(epic.getEpicSubtasks().isEmpty()) {
                epic.setTaskStatus(TaskStatus.NEW);
                return;
            }
            boolean isDone = false;
            boolean isNew = false;
            boolean isInProgress = false;

            for (Integer id : epic.getEpicSubtasks()) {
                switch (subtasks.get(id).getTaskStatus()) {
                    case NEW:
                        isNew = true;
                        break;
                    case DONE:
                        isDone = true;
                        break;
                    case IN_PROGRESS:
                        isInProgress = true;
                        break;
                }
            }

            if (!isNew && !isInProgress) {
                epic.setTaskStatus(TaskStatus.DONE);
            } else if (!isInProgress && !isDone) {
                epic.setTaskStatus(TaskStatus.NEW);
            } else {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            }
        }



    // удаление всех задач
    public void deleteAllTasks(){
        tasks.clear();
    }

    public void deleteAllEpics(){
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks(){
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksOfEpic();
            changeEpicStatus(epic);
        }
        subtasks.clear();
    }



    // получение задачи по идентификатору
    public Task getTaskById(int id) {
        return tasks.get(id) ;
    }

    public Epic getEpicById(int id) {
        return  epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    // получение списка задач определенного эпика
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
        for (Integer subtaskId : epics.get(epicId).getEpicSubtasks()) {
            if(subtasks.containsKey(subtaskId)) {
                subtasksOfEpic.add(subtasks.get(subtaskId));
            }
        }
        return subtasksOfEpic;
    }



    // получение списка всех задач
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(tasks.get(task.getId()));
        }
      return allTasks;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epics.get(epic.getId()));
        }
      return allEpics;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtasks.get(subtask.getId()));
        }
        return allSubtasks;
    }

    // удаление по id

    public void deleteTaskById(int id){
            tasks.remove(id);
    }
///
    public void deleteEpicById(int id){
        if(epics.containsKey(id)) {
            for (Integer subtaskId : epics.get(id).getEpicSubtasks())
            subtasks.remove(subtaskId);
            epics.remove(Integer.valueOf(id));
        }
    }

    public void deleteSubtaskById(int id) {
        if(epics.containsKey(id)) {
            if (subtasks.containsKey(id)) {
                subtasks.remove(id);
            }

            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.deleteSubtask(id);
            changeEpicStatus(epics.get(epic.getId()));
        }
    }


    // обновление задачи
    public void updateTask(Task updateTask){
        if(tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        }
    }

    public void updateSubtask(Subtask updateSubtask){
               if((subtasks.containsKey(updateSubtask.getId())) &&
                  (subtasks.get(updateSubtask.getId()).getEpicId() == updateSubtask.getEpicId())) {
                   subtasks.put(updateSubtask.getId(),updateSubtask);
                   changeEpicStatus(epics.get(updateSubtask.getEpicId()));
               }
    }

    public void updateEpic(Epic updateEpic){
        if(epics.containsKey(updateEpic.getId())) {
            epics.get(updateEpic.getId()).setName(updateEpic.getName());
            epics.get(updateEpic.getId()).setDescription(updateEpic.getDescription());
        }
    }
}




