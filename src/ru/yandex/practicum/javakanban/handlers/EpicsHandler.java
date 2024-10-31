package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.TaskManager;
import ru.yandex.practicum.javakanban.model.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    private TaskManager taskManager;

    public EpicsHandler(TaskManager taskManager) {
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
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();


        switch (exchange.getRequestMethod()) {
            case "GET":

                if (path.length < 2 || path.length > 4) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                if (!path[1].equals("epics")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                if (path.length == 2) {
                    response = gson.toJson(taskManager.getEpics());
                    sendText(exchange,response);
                    break;
                }

                if (id.isEmpty() || !taskManager.getEpics().containsKey(id.get())) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    if (path.length == 3) {
                        response = gson.toJson(taskManager.getEpicById(id.get()));
                        sendText(exchange, response);
                        break;
                    } else {
                        if (path[3].equals("subtasks")) {
                            response = gson.toJson(taskManager.getSubtasksOfEpic(id.get()));
                            sendText(exchange, response);
                            break;
                        } else {
                            sendNotFound(exchange, "Путь не найден.");
                            break;
                        }
                    }
                }

            case "POST":
                if (path.length != 2 || !path[1].equals("epics")) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }

                Optional<String> resp;

                try {
                    resp = Optional.of(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
                } catch (IOException e) { // тип ошибки
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                Epic epic;

                try {
                    epic = gson.fromJson(resp.get(), Epic.class);
                } catch (RuntimeException e) { // тип ошибки
                    sendNotFound(exchange, "ошибка");
                    break;
                }

                taskManager.addNewEpic(epic);
                response = gson.toJson(taskManager.getEpicById(epic.getId()));
                sendText201(exchange, response);
                break;

            case "DELETE":
                if (id.isEmpty()) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                }
                if (path.length != 3 || !path[1].equals("epics") || !taskManager.getEpics().containsKey(id.get())) {
                    sendNotFound(exchange, "Путь не найден.");
                    break;
                } else {
                    taskManager.deleteEpicById(id.get());
                    sendText(exchange, "Действие выполнено корректно.");
                    break;
                }
            default: sendNotFound(exchange, "Такого действия нет.");
        }
    }
}





