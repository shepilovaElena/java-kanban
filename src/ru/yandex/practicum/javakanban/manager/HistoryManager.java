package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Task;

import java.util.List;

public interface HistoryManager {
   List<Task> getHistory();

   void addTaskInHistory(Task task);

   void removeTaskFromHistory(int id);
}
