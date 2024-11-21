package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public PrioritizedHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        if (exchange.getRequestMethod().equals("GET") && exchange.getRequestURI().getPath().equals("/prioritized")) {
            response = gson.toJson(taskManager.getPrioritizedTasks());
            sendText(exchange, response);
        } else {
            sendNotFound(exchange, "Такого пути нет.");
        }
    }
}


