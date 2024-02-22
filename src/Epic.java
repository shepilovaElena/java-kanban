import java.util.ArrayList;


public class Epic extends Task {
    private ArrayList<Integer> epicSubtasks;
    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        epicSubtasks = new ArrayList<>();
    }

    public void addNewSubtask(Subtask subtask){
      epicSubtasks.add(subtask.getId());
    }

    public void deleteSubtask(int id){
      epicSubtasks.remove(id);
    }

    public void deleteAllSubtasksOfEpic(){
        epicSubtasks.clear();
    }

    public ArrayList<Integer> getEpicSubtasks() {
         ArrayList<Integer> subtasks = new ArrayList<>();
         subtasks.addAll(epicSubtasks);
        return subtasks;
    }
}