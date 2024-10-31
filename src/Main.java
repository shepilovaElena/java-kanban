
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.InMemoryTaskManager;
import ru.yandex.practicum.javakanban.handlers.TasksHandler;
import ru.yandex.practicum.javakanban.handlers.SubtasksHandler;
import ru.yandex.practicum.javakanban.handlers.EpicsHandler;
import ru.yandex.practicum.javakanban.handlers.HistoryHandler;
import ru.yandex.practicum.javakanban.handlers.PrioritizedHandler;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static HistoryManager historyManager;

    public Main(InMemoryTaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/tasks", new TasksHandler(taskManager));
        httpServer.createContext("/subtasks", new SubtasksHandler(taskManager));
        httpServer.createContext("/epics", new EpicsHandler(taskManager));
        httpServer.createContext("/history", new HistoryHandler(historyManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));
        startServer(httpServer);

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void startServer(HttpServer httpServer) {
        httpServer.start();
    }


}





