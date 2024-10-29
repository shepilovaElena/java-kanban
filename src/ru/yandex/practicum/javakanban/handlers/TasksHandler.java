package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public TasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
                    sendText(exchange,"Действие выполнено корректно.");
                    break;
                }
                if (path.length == 3 && path[1].equals("tasks")) {
                    if (id.isEmpty() || !taskManager.getTasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        sendText(exchange, "Действие выполнено корректно.");
                        break;
                    }
                }

            case "POST": ///// переделать
                if (path.length < 2 || path.length > 3) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }
                if (path.length == 2 && path[1].equals("tasks")) {
                    sendText201(exchange,"Действие выполнено корректно.");
                    break;
                }
                if (path.length == 3 && path[1].equals("tasks")) {
                    if (id.isEmpty() || taskManager.getTasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        sendText201(exchange, "Действие выполнено корректно.");
                        break;
                    }
                }
            case "DELETE":
                if (path.length != 3 && path[1].equals("tasks")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    if (id.isEmpty() || !taskManager.getTasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        sendText(exchange, "Действие выполнено корректно.");
                        break;
                    }
                }
            default: sendNotFound(exchange, "Такого действия нет.");
        }

    }

}

