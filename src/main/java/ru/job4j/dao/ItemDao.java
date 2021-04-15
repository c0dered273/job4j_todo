package ru.job4j.dao;

import ru.job4j.model.Item;

import java.util.List;

public interface ItemDao {
    void save(Item item);

    void update(Item item);

    void delete(Item item);

    List<Item> findAll();

    Item findById(long id);
}
