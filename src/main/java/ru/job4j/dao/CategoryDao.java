package ru.job4j.dao;

import java.util.List;
import ru.job4j.model.Category;

/**
 * Методы для доступа к сущностям категорий задач.
 */
public interface CategoryDao extends Dao {
    Category findById(long id);

    List<Category> findAll();
}
