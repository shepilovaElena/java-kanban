package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Task;

import java.util.*;

public class InMemoryHistoryTaskManager implements HistoryManager {
    private List<Task> publicHistory;
    private Map<Integer, Node<Task>> history = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;
    private Node<Task> newView;


    @Override
    public List<Task> getHistory() {
        publicHistory = new ArrayList<>();
        publicHistory.add(head.data);
        Node<Task> node = head;
        while (node.next != null) {
            publicHistory.add(node.next.data);
            node = node.next;
        }
        return publicHistory;
    }

    @Override
    public void addTaskInHistory(Task task) {

        if (history.size() == 0) {
            head = new Node<>(task);
            history.put(task.getId(), head);

        } else if ((history.size() == 1) && !(history.containsKey(task.getId()))) {
            tail = new Node<>(task);
            head.next = tail;
            tail.prev = head;
            tail.next = null;
            history.put(task.getId(), tail);

        } else if (!(history.containsKey(task.getId()))) {
            newView = new Node<>(task);
            Node<Task> duplicateNode = tail;
            tail = newView;
            duplicateNode.next = tail;
            tail.prev = duplicateNode;
            history.put(task.getId(), tail);

        } else {
            removeNode(history.get(task.getId()));
            Node<Task> node = history.get(task.getId());
            Node<Task> duplicateNode = tail;
            duplicateNode.next = node;
            node.prev = duplicateNode;
            tail = node;

        }
    }

    @Override
    public void removeNode(Node<Task> node) {
        if (node == head) {
            node.next.prev = null;
            head = node.next;
            node.next = null;
            history.put(head.data.getId(), head);
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
            node.prev = null;
            history.put(node.data.getId(), tail);
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
        }
    }

}