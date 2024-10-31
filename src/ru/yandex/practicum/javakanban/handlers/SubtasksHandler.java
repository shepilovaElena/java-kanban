package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public SubtasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response;
        Optional<Integer> id = getTasksId(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();


        switch (exchange.getRequestMethod()) {
            case "GET":
                if (path.length < 2 || path.length > 3) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                if (!path[1].equals("subtasks")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                if (path.length == 2) {
                    response = gson.toJson(taskManager.getSubtasks());
                    sendText(exchange,response);
                    break;
                } else {
                    if (id.isEmpty() || !taskManager.getSubtasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        response = gson.toJson(taskManager.getSubtaskById(id.get()));
                        sendText(exchange, response);
                        break;
                    }
                }

            case "POST":
                if (path.length < 2 || path.length > 3) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                Optional<String> resp;

                try {
                    resp = Optional.of(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
                } catch (IOException e) {       // тип ошибки
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                Subtask subtask;

                try {
                    subtask = gson.fromJson(resp.get(), Subtask.class);
                } catch (RuntimeException e) {          // тип ошибки
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                if (!path[1].equals("subtasks")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    if (path.length == 2) {
                        Optional<Integer> taskId = taskManager.addNewSubtask(subtask);
                        if (taskId.isEmpty()) {
                            sendHasInteractions(exchange, "Задача пересекаются по времени с уже существующими.");
                            break;
                        } else {
                            sendText201(exchange, "Действие выполнено корректно.");
                            break;
                        }
                    } else {
                        if (id.isEmpty() || !taskManager.getSubtasks().containsKey(id.get())) {
                            sendNotFound(exchange,"Путь не найден.");
                            break;
                        } else {
                            taskManager.updateSubtask(subtask);
                            sendText201(exchange, "Действие выполнено корректно.");
                            break;
                        }
                    }
                }

            case "DELETE":
                if (id.isEmpty()) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }
                if (path.length != 3 || !path[1].equals("subtasks") || !taskManager.getSubtasks().containsKey(id.get())) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    taskManager.deleteSubtaskById(id.get());
                    sendText(exchange, "Действие выполнено корректно.");
                    break;
                }
            default: sendNotFound(exchange, "Такого действия нет.");
        }
    }
}


