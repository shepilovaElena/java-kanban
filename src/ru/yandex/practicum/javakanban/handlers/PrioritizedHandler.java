package ru.yandex.practicum.javakanban.handlers;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public PrioritizedHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET") && exchange.getRequestURI().getPath().equals("/prioritized")) {
            sendText(exchange, "Действие выполнено корректно.");
        } else {
            sendNotFound(exchange, "Такого действия нет.");
        }
    }

}
