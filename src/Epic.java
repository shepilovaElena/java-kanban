import java.util.ArrayList;


public class Epic extends Task {
    ArrayList<Subtask> epicSubtasks;
    public Epic(String name, String description) {
        super(name, description);
        epicSubtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getEpicSubtasks() {
        return epicSubtasks;
    }
}