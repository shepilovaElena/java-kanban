import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtasks = new ArrayList<>();
    public Epic(String name, String description, ArrayList<Subtask> subtasks) {
        super(name, description);
        this.subtasks.addAll(subtasks);
    }
}
