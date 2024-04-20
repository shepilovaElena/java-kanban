package ru.yandex.practicum.javakanban.model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private TaskStatus taskStatus;
    private int id;


    public Task(String name, String description, TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, TaskStatus taskStatus, int id) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
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
}
