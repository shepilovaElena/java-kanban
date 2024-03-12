package Model;

import java.util.Objects;

public class Subtask extends Task {

   private int epicId;


   public Subtask(String name, String description, int epicId, TaskStatus taskStatus){
          super(name, description, taskStatus);
          this.epicId = epicId;
   }

   public Subtask(String name, String description, int epicId, TaskStatus taskStatus, int id){
      super(name, description, taskStatus, id);
      this.epicId = epicId;
   }


   public int getEpicId() {
      return epicId;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      if (!super.equals(o)) return false;
      Subtask subtask = (Subtask) o;
      return epicId == subtask.epicId;
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), epicId);
   }

   @Override
   public String toString() {
      return "Model.Subtask{name='" + getName() + "', " +
              "epicId=" + epicId +
              '}';
   }
}
