package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Epic;
import ru.yandex.practicum.javakanban.model.Subtask;
import ru.yandex.practicum.javakanban.model.Task;
import ru.yandex.practicum.javakanban.model.TaskStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;
    private Path fileToHistory;

    public FileBackedTaskManager(Path file, Path fileToHistory) {
        super();
        this.fileToHistory = fileToHistory;
        this.file = file;
    }

    @Override
    public Task getTaskById(int id) {
        super.getTaskById(id);
        saveHistory();
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        super.getEpicById(id);
        saveHistory();
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        super.getSubtaskById(id);
        saveHistory();
        return subtasks.get(id);
    }


    @Override
    public int addNewTask(Task newTask) {
        super.addNewTask(newTask);
        save();
        return newTask.getId();
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        super.addNewEpic(newEpic);
        save();
        return newEpic.getId();
    }

    @Override
    public int addNewSubtask(Subtask newSubtask) {
        super.addNewSubtask(newSubtask);
        save();
        return newSubtask.getId();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
        saveHistory();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
        saveHistory();
    }


    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
        saveHistory();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
        saveHistory();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
        saveHistory();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
        saveHistory();
    }

    // обновление задачи
    @Override
    public void updateTask(Task updateTask) {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        super.updateSubtask(updateSubtask);
        save();
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        super.updateEpic(updateEpic);
        save();
    }


    private void save() throws ManagerSaveException {
        List<String> lines = new ArrayList<>();
        try {
            if (!tasks.isEmpty()) {
                for (Task task : tasks.values()) {
                    lines.add(taskToString(task));
                }
            }
            if (!epics.isEmpty()) {
                for (Epic epic : epics.values()) {
                    lines.add(taskToString(epic));
                }
            }
            if (!subtasks.isEmpty()) {
                for (Subtask subtask : subtasks.values()) {
                    lines.add(taskToString(subtask));
                }
            }
            FileWriter writer = new FileWriter(file.toFile(), StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("id,type,name,status,description,epic");
            bw.newLine();
            for (String str : lines) {
                bw.write(str + "\n");
            }
            bw.close();
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка записи в файл.");
        }
    }

    private void saveHistory() throws ManagerSaveException {
        try {
            FileWriter writer = new FileWriter(fileToHistory.toFile(), StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(writer);
            for (Task task : historyManager.getHistory()) {
                bw.write(task.getId() + ",");
            }
            bw.close();
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка записи в файл.");
        }
    }

    public String taskToString(Task task) {
        String str = task.getId() + "," + task.getTypeOfTask() + "," + task.getName() + "," + task.getTaskStatus() + "," + task.getDescription() + ",";
        if (task.getTypeOfTask().equals(TypeOfTask.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            str = str + subtask.getEpicId();
        }

        return str;
    }

    public void  loadFromFile() throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(file.toFile(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);

        while (br.ready()) {
            String line = br.readLine();
            lines.add(line);
        }
        br.close();
        int maxId = 0;
        for (int i = 1; i < lines.size(); i++) {
            int currentId = Integer.valueOf(lines.get(i).split(",")[0]);
            if (maxId < currentId) {
                maxId = currentId;
            }
            fromString(lines.get(i));
        }
        setId(maxId);
        for (Subtask subtask : subtasks.values()) {
           int epicId = subtask.getEpicId();
           Epic currentEpic = epics.get(epicId);
           currentEpic.getEpicSubtasks().add(subtask.getId());
        }
    }

    public void loadFromFileHistory() throws IOException {
        FileReader reader = new FileReader(fileToHistory.toFile(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);
        String[] historyId = new String[0];
        while (br.ready()) {
            String line = br.readLine();
            historyId = line.split(",");
        }
        br.close();
        for (int i = 0; i < historyId.length; i++) {
            int currentId = Integer.parseInt(historyId[i]);
            if (tasks.containsKey(currentId)) {
                historyManager.addTaskInHistory(tasks.get(currentId));
            }
            if (epics.containsKey(currentId)) {
                historyManager.addTaskInHistory(epics.get(currentId));
            }
            if (subtasks.containsKey(currentId)) {
                historyManager.addTaskInHistory(subtasks.get(currentId));
            }
        }
    }

    public static FileBackedTaskManager loadFromFile(Path file, Path fileToHistory) throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file, fileToHistory);
        fileBackedTaskManager.loadFromFile();
        fileBackedTaskManager.loadFromFileHistory();
        return fileBackedTaskManager;
    }

    private void fromString(String value) {
        String[] valueOfTasks = value.split(",");
        Task el;

        switch (valueOfTasks[1]) {
            case "Task":
                el = new Task(valueOfTasks[2], valueOfTasks[4], TaskStatus.valueOf(valueOfTasks[3]), Integer.parseInt(valueOfTasks[0]));
                        tasks.put(Integer.parseInt(valueOfTasks[0]), el);
            case "Epic":
                el = new Epic(valueOfTasks[2], valueOfTasks[4], Integer.parseInt(valueOfTasks[0]));
                         el.setTaskStatus(TaskStatus.valueOf(valueOfTasks[3]));
                         epics.put(Integer.parseInt(valueOfTasks[0]), (Epic) el);
            case "Subtask":
                el = new Subtask(valueOfTasks[2], valueOfTasks[4], Integer.parseInt(valueOfTasks[0]), TaskStatus.valueOf(valueOfTasks[3]),
                        Integer.parseInt(valueOfTasks[5]));
                subtasks.put(Integer.parseInt(valueOfTasks[0]), (Subtask) el);
        }
    }

    public Path getFile() {
        return file;
    }

    public Path getFileToHistory() {
        return fileToHistory;
    }

}

