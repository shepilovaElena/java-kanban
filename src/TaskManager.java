import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int id = 0;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    // генерация id
    public static int generateId() {
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

    public int addNewSubtask(Epic epic, Subtask newSubtask) {
        if(epics.containsKey(epic.getId())) {
            newSubtask.setId(generateId());
            epic.addNewSubtask(newSubtask);
            subtasks.put(newSubtask.getId(), newSubtask);
            changeEpicStatus(epic);

        }
        return newSubtask.getId();
    }

    // изменение статуса задачи

    private void changeEpicStatus(Epic epic) {
        for (Integer id : epic.getEpicSubtasks()){
            if((subtasks.get(id).getTaskStatus() != TaskStatus.DONE)) {
                epic.setTaskStatus(TaskStatus.IN_PROGRESS);
                return;
            } else {
                epic.setTaskStatus(TaskStatus.DONE);
            }
        }
    }


    // удаление всех задач
    public void deleteAllTasks(){
        tasks.clear();
    }

    public void deleteAllEpics(){
        epics.clear();
    }

    public void deleteAllSubtasks(){
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksOfEpic();
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
    public ArrayList<Integer> getSubtasksOfEpic(int epicId) {
        ArrayList<Integer> subtasksOfEpic = new ArrayList<>();
        for (Epic epic : epics.values()) {
            if (epic.getId() == epicId) {
               subtasksOfEpic.addAll(epic.getEpicSubtasks());
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
    public void deleteById(int id){
        if(tasks.containsKey(id)) {
        tasks.remove(id);
        return;
        }
        for (Epic epic : epics.values()){
                if(epic.getEpicSubtasks().contains(getTaskById(id))){
                    epic.getEpicSubtasks().remove(getTaskById(id));
                    epic.deleteSubtask(id);
                return;
            }
        }
        if (epics.containsKey(id)) {
            epics.remove(id);
            for(Subtask subtask : subtasks.values()) {
                if(subtask.getEpicId() == id) {
                    subtasks.remove(subtask.getId());
                }
            }
        }

    }

    // обновление задачи
    public void updateTask(Task updateTask){
        if(tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        }
    }

    public void updateSubtask(Subtask updateSubtask){
               if((subtasks.containsKey(updateSubtask.getId()))) {
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




