package ru.yandex.practicum.javakanban.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.javakanban.manager.HistoryManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private HistoryManager historyManager;

    public HistoryHandler(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        String[] path = exchange.getRequestURI().getPath().split("/");

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        if (exchange.getRequestMethod().equals("GET") && exchange.getRequestURI().getPath().equals("/history")) {
            response = gson.toJson(historyManager.getHistory());
            sendText(exchange, response);
        } else {
            sendNotFound(exchange, "Такого действия нет.");
        }
    }
}