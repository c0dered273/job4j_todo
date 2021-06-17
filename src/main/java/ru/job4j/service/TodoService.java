package ru.job4j.service;

import java.util.List;
import ru.job4j.model.Category;
import ru.job4j.model.User;

/**
 * Обеспечивает работу с задачами.
 */
public interface TodoService {
    String getAllItemsJsonString(User user);

    String getAllUndoneJsonString(User user);

    boolean setDone(long id, boolean flag);

    boolean newTask(String description, User user, List<Category> categories);
}
