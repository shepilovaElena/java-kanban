package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Task;

import java.util.Comparator;

public class SubtasksComparator implements Comparator<Task> {
    @Override
    public int compare(Task subtask1, Task subtask2) {
        if (subtask1.getStartTime().isBefore(subtask2.getStartTime())) {
            return -1;
        } else if (subtask1.getStartTime().isAfter(subtask2.getStartTime())) {
            return 1;
        } else return 0;
    }
}
