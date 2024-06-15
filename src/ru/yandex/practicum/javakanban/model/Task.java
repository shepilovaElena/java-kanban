package ru.yandex.practicum.javakanban.model;

import ru.yandex.practicum.javakanban.manager.TypeOfTask;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private TaskStatus taskStatus;

    private TypeOfTask typeOfTask = TypeOfTask.TASK;
    private int id;


    public Task(String name, String description, TaskStatus taskStatus, TypeOfTask typeOfTask) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, TaskStatus taskStatus, int id, TypeOfTask typeOfTask) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, int id, TypeOfTask typeOfTask) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.typeOfTask = TypeOfTask.TASK;
        this.taskStatus = taskStatus;
    }

    public Task(String name, String description, TaskStatus taskStatus, int id) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = TypeOfTask.TASK;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = TypeOfTask.TASK;
    }





    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public int getId() {
        return id;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setId(int id) {
        this.id = id;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.javakanban.model.Task{" +
                "name='" + name + '\'' +
                '}';
    }

    public String taskToString() {
        return id + "," + typeOfTask + "," + name + "," + taskStatus + "," + description + ",";
    }

   }
