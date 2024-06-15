package ru.yandex.practicum.javakanban.model;

import ru.yandex.practicum.javakanban.manager.TypeOfTask;

import java.util.ArrayList;


public class Epic extends Task {
    private ArrayList<Integer> epicSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW, TypeOfTask.EPIC);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id, TypeOfTask.EPIC);
    }

    public void addNewSubtask(int id) {
        epicSubtasks.add(Integer.valueOf(id));
    }

    public void deleteSubtask(int id) {
        epicSubtasks.remove(Integer.valueOf(id));
    }

    public void deleteAllSubtasksOfEpic() {
        epicSubtasks.clear();
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    @Override
    public String taskToString() {
        return super.taskToString();
    }

}