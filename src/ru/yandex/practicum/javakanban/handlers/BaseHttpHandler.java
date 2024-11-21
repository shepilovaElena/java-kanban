package ru.yandex.practicum.javakanban.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class BaseHttpHandler {

    public void sendText(HttpExchange exchange, String resp) throws IOException {
        byte [] response = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    public void sendText201(HttpExchange exchange, String resp) throws IOException {
        byte [] response = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(201, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    public void sendNotFound(HttpExchange exchange, String resp) throws IOException {
        byte [] response = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(404, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();

    }

    public void sendHasInteractions(HttpExchange exchange, String resp) throws IOException {
        byte [] response = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(406, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    protected Optional<Integer> getTasksId(HttpExchange exchange) {
        String[] path = exchange.getRequestURI().getPath().split("/");
        if (path.length >= 3) {
            try {
                return Optional.of(Integer.parseInt(path[2]));
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}


