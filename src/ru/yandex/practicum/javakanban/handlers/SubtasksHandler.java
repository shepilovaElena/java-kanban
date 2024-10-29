package ru.yandex.practicum.javakanban.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;

import java.io.IOException;
import java.util.Optional;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public SubtasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTasksId(exchange);
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (exchange.getRequestMethod()) {
            case "GET":
                if (path.length < 2 || path.length > 3) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }
                if (path.length == 2 && path[1].equals("subtasks")) {
                    sendText(exchange,"Действие выполнено корректно.");
                    break;
                }
                if (path.length == 3 && path[1].equals("subtasks")) {
                    if (id.isEmpty() || !taskManager.getSubtasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        sendText(exchange, "Действие выполнено корректно.");
                        break;
                    }
                }

            case "POST":
                if (path.length < 2 || path.length > 3) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }
                if (path.length == 2 && path[1].equals("subtasks")) {
                    sendText201(exchange,"Действие выполнено корректно.");
                    break;
                }
                if (path.length == 3 && path[1].equals("subtasks")) {
                    if (id.isEmpty() || !taskManager.getSubtasks().containsKey(id.get())) {
                        sendNotFound(exchange, "Путь не найден.");
                        break;
                    } else {
                        sendText201(exchange, "Действие выполнено корректно.");
                        break;
                    }
                }
            case "DELETE":
                if (path.length != 3 || !path[1].equals("subtasks")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    if (id.isEmpty() || !taskManager.getSubtasks().containsKey(id.get())) {
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

