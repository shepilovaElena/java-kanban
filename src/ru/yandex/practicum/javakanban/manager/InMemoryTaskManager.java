package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    private Managers manager = new Managers();
    protected HistoryManager historyManager = manager.getDefaultHistory();



    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setId(int newId) {
        id = newId;
    }

    // генерация id
    private int generateId() {
        id++;
        return id;
    }

    // добавление новых задач
    @Override
    public int addNewTask(Task newTask) {
        newTask.setId(generateId());
        tasks.put(newTask.getId(), newTask);
        return newTask.getId();
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        newEpic.setId(generateId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic.getId();
    }

    @Override
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

        if (epic.getEpicSubtasks().isEmpty()) {
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
    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.removeTaskFromHistory(id);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        if (epics.size() != 0) {
            for (Integer epicId : epics.keySet()) {
                if (epics.get(epicId).getEpicSubtasks().size() != 0 ||
                        epics.get(epicId).getEpicSubtasks() != null) {
                    for (Integer subtaskId : epics.get(epicId).getEpicSubtasks()) {
                        historyManager.removeTaskFromHistory(subtaskId);
                    }
                }
                historyManager.removeTaskFromHistory(epicId);
            }
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            for (int id : epic.getEpicSubtasks()) {
                historyManager.removeTaskFromHistory(id);
            }
        }
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksOfEpic();
            changeEpicStatus(epic);
        }
        subtasks.clear();
    }



    // получение задачи по идентификатору
    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addTaskInHistory(tasks.get(id));
        }
        return tasks.get(id);
    }


    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            historyManager.addTaskInHistory(epics.get(id));
        }
        return  epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.addTaskInHistory(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    // получение списка задач определенного эпика
    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
        for (Integer subtaskId : epics.get(epicId).getEpicSubtasks()) {
            if (subtasks.containsKey(subtaskId)) {
                subtasksOfEpic.add(subtasks.get(subtaskId));
            }
        }
        return subtasksOfEpic;
    }



    // получение списка всех задач
    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(tasks.get(task.getId()));
        }
        return allTasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epics.get(epic.getId()));
        }
        return allEpics;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtasks.get(subtask.getId()));
        }
        return allSubtasks;
    }

    // удаление по id
    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
        historyManager.removeTaskFromHistory(id);
    }


    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            for (Integer subtaskId : epics.get(id).getEpicSubtasks()) {
                subtasks.remove(subtaskId);
            }
            historyManager.removeTaskFromHistory(id);
            epics.remove(Integer.valueOf(id));
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (epics.containsKey(id)) {
            if (subtasks.containsKey(id)) {
                subtasks.remove(id);
                historyManager.removeTaskFromHistory(id);
            }

            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.deleteSubtask(id);
            changeEpicStatus(epics.get(epic.getId()));
        }
    }


    // обновление задачи
    @Override
    public void updateTask(Task updateTask) {
        if (tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        }
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        if ((subtasks.containsKey(updateSubtask.getId())) &&
                (subtasks.get(updateSubtask.getId()).getEpicId() == updateSubtask.getEpicId())) {
            subtasks.put(updateSubtask.getId(),updateSubtask);
            changeEpicStatus(epics.get(updateSubtask.getEpicId()));
        }
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        if (epics.containsKey(updateEpic.getId())) {
            epics.get(updateEpic.getId()).setName(updateEpic.getName());
            epics.get(updateEpic.getId()).setDescription(updateEpic.getDescription());
        }
    }
}
