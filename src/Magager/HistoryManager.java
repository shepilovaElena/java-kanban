package Magager;

import Model.Task;

import java.util.List;

public interface HistoryManager {
   List<Task> getHistory();

   void addTaskInHistory(Task task);

}
