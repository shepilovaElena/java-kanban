package Magager;

import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryTaskManager implements HistoryManager{
    private List<Task> history = new ArrayList<>();

    // история просмотров
    @Override
    public List<Task> getHistory(){

        return history;
    }

    @Override
    public void addTaskInHistory(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

}
