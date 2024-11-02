
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.InMemoryTaskManager;
import ru.yandex.practicum.javakanban.handlers.TasksHandler;
import ru.yandex.practicum.javakanban.handlers.SubtasksHandler;
import ru.yandex.practicum.javakanban.handlers.EpicsHandler;
import ru.yandex.practicum.javakanban.handlers.HistoryHandler;
import ru.yandex.practicum.javakanban.handlers.PrioritizedHandler;
import ru.yandex.practicum.javakanban.manager.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {
    private static Managers manager = new Managers();

    private static InMemoryTaskManager taskManager;
    private static HistoryManager historyManager;



    public static void main(String[] args) throws IOException {

        HttpTaskServer httpTaskServer = new HttpTaskServer(manager.getDefault());

        httpTaskServer.startServer();
        System.out.println("HTTP-сервер запущен на " + 8080 + " порту!");
        httpTaskServer.stopServer(httpTaskServer.getHttpServer());
    }



    }







