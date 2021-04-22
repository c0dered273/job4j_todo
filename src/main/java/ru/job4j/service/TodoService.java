package ru.job4j.service;

import ru.job4j.model.User;

public interface TodoService {
    String getAllItemsJsonString(User user);
    String getAllUndoneJsonString(User user);
    boolean setDone(long id, boolean flag);
    boolean newTask(String description, User user);
}
