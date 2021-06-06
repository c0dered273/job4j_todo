package ru.job4j.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.CategoryDao;
import ru.job4j.dao.CategoryDaoImpl;
import ru.job4j.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public String getAllCategoriesJsonString() {
        var result = "";
        try {
            List<Category> categories = categoryDao.findAll();
            result = new ObjectMapper().writeValueAsString(categories);
        } catch (HibernateException e) {
            logger.error("Can't get all categories from DB", e);
        } catch (JsonProcessingException e) {
            logger.error("Can't map list of categories to json", e);
        }
        return result;
    }

    @Override
    public List<Category> getCategoriesFromIdArray(String[] idArr) {
        List<Category> result = new ArrayList<>();
        for (String id : idArr) {
            Category cat = null;
            try {
                cat = categoryDao.findById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                logger.error("Can't convert String={} to id", id, e);
            }
            result.add(cat);
        }
        return result;
    }
}
