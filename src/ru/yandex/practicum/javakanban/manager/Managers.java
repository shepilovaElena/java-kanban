package ru.yandex.practicum.javakanban.manager;

import java.nio.file.Path;

public class Managers  {

    public  TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public  HistoryManager getDefaultHistory() {
       return new InMemoryHistoryTaskManager();
    }

    public FileBackedTaskManager getDefaultFileBackedTaskManager(Path file, Path fileToHistory) {
        return new FileBackedTaskManager(file, fileToHistory);
    }
}
