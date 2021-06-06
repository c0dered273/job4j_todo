package ru.job4j.service;

import ru.job4j.model.Category;

import java.util.List;

public interface CategoryService {
    String getAllCategoriesJsonString();
    List<Category> getCategoriesFromIdArray(String[] idArr);
}
