package ru.yandex.practicum.javakanban.model;

import ru.yandex.practicum.javakanban.manager.TypeOfTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private TaskStatus taskStatus;

    private TypeOfTask typeOfTask;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;



    public Task(String name, String description, TaskStatus taskStatus, TypeOfTask typeOfTask) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, int id, TaskStatus taskStatus, TypeOfTask typeOfTask) {
        this.id = id;
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, TaskStatus taskStatus, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.typeOfTask = typeOfTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, TaskStatus taskStatus, int id, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = typeOfTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, int id, TypeOfTask typeOfTask) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = typeOfTask;
    }

    public Task(String name, String description, int id, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = typeOfTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.typeOfTask = TypeOfTask.TASK;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, TaskStatus taskStatus, int id, Duration duration, LocalDateTime startTime) {
        this.taskStatus = taskStatus;
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = TypeOfTask.TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, int id, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.typeOfTask = TypeOfTask.TASK;
        this.duration = duration;
        this.startTime = startTime;
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

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
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

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

   }
