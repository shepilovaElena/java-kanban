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
            history.put(task.getId(), tail);

        } else if (!(history.containsKey(task.getId()))) {
            newView = new Node<>(task);
            Node<Task> duplicate = tail;
            tail = newView;
            duplicate.next = tail;
            tail.prev = duplicate;
            history.put(task.getId(), tail);

        } else {
            removeNode(history.get(task.getId()));
            Node<Task> node = history.get(task.getId());
            Node<Task> duplicateNode = tail;
            node.prev = duplicateNode;
            duplicateNode.next = node;
            history.put(task.getId(), node);
            tail = node;
        }
    }

    @Override
    public void removeNode(Node<Task> node) {
        if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
            node.next = null;
        } else if (node.next != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
        }
    }

}