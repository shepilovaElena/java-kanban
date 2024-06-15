package ru.yandex.practicum.javakanban.model;

import ru.yandex.practicum.javakanban.manager.TypeOfTask;

import java.util.Objects;

public class Subtask extends Task {

   private int epicId;


   public Subtask(String name, String description, int epicId, TaskStatus taskStatus) {
          super(name, description, taskStatus, TypeOfTask.SUBTASK);
          this.epicId = epicId;
   }

   public Subtask(String name, String description, int epicId, TaskStatus taskStatus, int id) {
      super(name, description, taskStatus, id, TypeOfTask.SUBTASK);
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
      return "ru.yandex.practicum.javakanban.model.Subtask{name='" + getName() + "', " +
              "epicId=" + epicId +
              '}';
   }

   @Override
   public String taskToString() {
      return super.taskToString() + epicId;
   }

}
