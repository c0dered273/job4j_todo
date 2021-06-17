package ru.job4j.dao;

import java.util.List;
import ru.job4j.model.Item;
import ru.job4j.model.User;

/**
 * Методы доступа к сущностям задача.
 */
public interface ItemDao extends Dao {
    void save(Item item);

    void update(Item item);

    void delete(Item item);

    List<Item> findAll();

    List<Item> findAll(User user);

    List<Item> findAllUndone();

    List<Item> findAllUndone(User user);

    Item findById(long id);
}
