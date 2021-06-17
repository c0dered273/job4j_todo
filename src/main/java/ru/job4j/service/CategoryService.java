package ru.job4j.service;

import java.util.List;
import ru.job4j.model.Category;

/**
 * Обеспечивает работу с категориями.
 */
public interface CategoryService {
    String getAllCategoriesJsonString();

    List<Category> getCategoriesFromIdArray(String[] idArr);
}
