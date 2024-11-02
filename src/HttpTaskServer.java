import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import ru.yandex.practicum.javakanban.handlers.*;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.TaskManager;


public class HttpTaskServer {
    private HttpServer httpServer;
    private TaskManager taskManager;
    private HistoryManager historyManager;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public HttpTaskServer(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }


    public void startServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TasksHandler(taskManager));
        httpServer.createContext("/epics", new EpicsHandler(taskManager));
        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));
        httpServer.createContext("/history", new HistoryHandler(historyManager));

        httpServer.start();
    }

    public void stopServer(HttpServer httpServer) {
        httpServer.stop(0);
    }

}
