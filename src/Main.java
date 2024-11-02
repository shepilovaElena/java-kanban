
import ru.yandex.practicum.javakanban.manager.HistoryManager;
import ru.yandex.practicum.javakanban.manager.InMemoryTaskManager;
import ru.yandex.practicum.javakanban.manager.Managers;

import java.io.IOException;



public class Main {
    private static Managers manager = new Managers();


    public static void main(String[] args) throws IOException {

        HttpTaskServer httpTaskServer = new HttpTaskServer(manager.getDefault());

        httpTaskServer.startServer();
        System.out.println("HTTP-сервер запущен на " + 8080 + " порту!");
        httpTaskServer.stopServer(httpTaskServer.getHttpServer());
    }



    }







