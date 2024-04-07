package ru.yandex.practicum.javakanban.manager;

public class Managers  {

    public  TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public  HistoryManager getDefaultHistory() {
       return new InMemoryHistoryTaskManager();
    }
}
