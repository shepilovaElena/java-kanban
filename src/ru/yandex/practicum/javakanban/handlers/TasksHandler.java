package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public TasksHandler(TaskManager taskManager) {
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
                if (path.length == 2 && path[1].equals("tasks")) {
                    response = gson.toJson(taskManager.getTasks());
                    sendText(exchange,response);
                    break;
                }
                if (path.length == 3 && path[1].equals("tasks")) {
                    if (id.isEmpty() || !taskManager.getTasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        response = gson.toJson(taskManager.getTaskById(id.get()));
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
                } catch (IOException e) {  ///// проверить тип ошибок!
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                Task task;

                try {
                    task = gson.fromJson(resp.get(), Task.class);
                } catch (RuntimeException e) { //// тип ошибки!!!
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                if (!path[1].equals("tasks")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    if (path.length == 2) {
                        Optional<Integer> taskId = taskManager.addNewTask(task);
                        if (taskId.isEmpty()) {
                            sendHasInteractions(exchange, "Задача пересекаются по времени с уже существующими.");
                            break;
                        } else {
                            sendText201(exchange, "Действие выполнено корректно.");
                            break;
                        }
                    } else {
                        if (id.isEmpty() || !taskManager.getTasks().containsKey(id.get())) {
                            sendNotFound(exchange,"Путь не найден.");
                            break;
                        } else {
                            taskManager.updateTask(task);
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
                if (path.length != 3 || !path[1].equals("tasks") || !taskManager.getTasks().containsKey(id.get())) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    taskManager.deleteTaskById(id.get());
                    sendText(exchange, "Действие выполнено корректно.");
                    break;
                }
            default: sendNotFound(exchange, "Такого действия нет.");
        }
    }
}



