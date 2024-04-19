package ru.yandex.practicum.javakanban.manager;

import ru.yandex.practicum.javakanban.model.Task;

import java.util.*;

public class InMemoryHistoryTaskManager implements HistoryManager {

    Map<Integer, Node<Task>> history = new HashMap<>();
    Node<Task> head;
    Node<Task> tail;



    @Override
    public List<Task> getHistory() {
        List<Task> publicHistory = new ArrayList<>();
        Node<Task> node = head;
        if (history.size() != 0) {
            while (node.next != null) {
                publicHistory.add(node.data);
                node = node.next;
            }
            publicHistory.add(tail.data);
        }
        return publicHistory;
    }

    @Override
    public void removeNode(Node<Task> node) {
        if (history.size() == 1) {
            node.next = null;
        } else if (history.size() > 1) {
            if (node == head) {
                node.next.prev = null;
                head = node.next;
            } else if (node == tail) {
                node.prev.next = null;
                tail = node.prev;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
    }

    @Override
    public void addTaskInHistory(Task task) {
        Node<Task> newView = new Node<>(task);
        if (history.size() == 0) {
            head = newView;
            history.put(task.getId(), head);
        } else if (history.size() == 1 && !history.containsKey(task.getId())) {
            tail = newView;
            head.next = tail;
            tail.prev = head;
            history.put(task.getId(), tail);
        } else if (!history.containsKey(task.getId())) {
            Node<Task> duplicateNode = tail;
            tail = newView;
            duplicateNode.next = tail;
            tail.prev = duplicateNode;
            history.put(task.getId(), tail);
        } else {
            Node<Task> node = history.get(task.getId());
            removeNode(history.get(task.getId()));
            Node<Task> duplicatNode = tail;
            node.prev = duplicatNode;
            node.next = null;
            duplicatNode.next = node;
            tail = node;
        }
    }

    @Override
    public void removeTaskFromHistory(int id) {
        if (history.containsKey(id)) {
            removeNode(history.get(id));
            history.remove(id);
        }
    }

}