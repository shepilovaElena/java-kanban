import java.util.ArrayList;

public class InMemoryHistoryTaskManager implements HistoryManager{
    private ArrayList<Task> history = new ArrayList<>();

    // история просмотров
    @Override
    public ArrayList<Task> getHistory(){
        return new ArrayList<>(history);
    }

    @Override
    public void add(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }

}
