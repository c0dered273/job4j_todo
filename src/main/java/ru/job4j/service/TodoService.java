package ru.job4j.service;

public interface TodoService {
    String getAllItemsJsonString();
    String getAllUndoneJsonString();
    boolean setDone(long id, boolean flag);
    boolean newTask(String description);
}
