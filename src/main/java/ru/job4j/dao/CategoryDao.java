package ru.job4j.dao;

import ru.job4j.model.Category;
import ru.job4j.model.Item;

import java.util.List;

public interface CategoryDao extends Dao {
    Category findById(long id);
    List<Category> findAll();
}
