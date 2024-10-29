
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.javakanban.manager.FileBackedTaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;
import ru.yandex.practicum.javakanban.handlers.TasksHandler;
import ru.yandex.practicum.javakanban.handlers.SubtasksHandler;
import ru.yandex.practicum.javakanban.handlers.EpicsHandler;
import ru.yandex.practicum.javakanban.handlers.HistoryHandler;
import ru.yandex.practicum.javakanban.handlers.PrioritizedHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        File backFile = File.createTempFile("back-test", ".csv", new File("src/resources"));
        File backHistoryFile = File.createTempFile("back-history-test", ".csv", new File("src/resources"));

        Managers manager = new Managers();
        FileBackedTaskManager fileBackedTaskManager = manager.getDefaultFileBackedTaskManager(backFile.toPath(), backHistoryFile.toPath());

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler(fileBackedTaskManager));
        httpServer.createContext("/subtasks", new SubtasksHandler(fileBackedTaskManager));
        httpServer.createContext("/epics", new EpicsHandler(fileBackedTaskManager));
        httpServer.createContext("/history", new HistoryHandler(fileBackedTaskManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(fileBackedTaskManager));
        startServer(httpServer);

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void startServer(HttpServer httpServer) {
        httpServer.start();
    }

    public static void stopServer(HttpServer httpServer) {
        httpServer.stop(1);
    }
}





