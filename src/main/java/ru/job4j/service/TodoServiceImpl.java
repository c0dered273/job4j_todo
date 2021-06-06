package ru.job4j.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.ItemDao;
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class TodoServiceImpl implements TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);
    private final ItemDao itemDao;

    public TodoServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public String getAllItemsJsonString(User user) {
        return getItems(user,false);
    }

    @Override
    public String getAllUndoneJsonString(User user) {
        return getItems(user, true);
    }

    @Override
    public boolean setDone(long id, boolean flag) {
        var result = false;
        var item = itemDao.findById(id);
        if (item != null) {
            item.setDone(flag);
            try {
                itemDao.update(item);
            } catch (HibernateException e) {
                logger.error("Can't update item with id={}", id, e);
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean newTask(String description, User user, List<Category> categories) {
        var result = false;
        if (categories.isEmpty()) return false;
        var item = Item.of(description, Timestamp.from(Instant.now()), false, user, categories);
        try {
            itemDao.save(item);
            result = true;
        } catch (Exception e) {
            logger.error("Can't save new item. Item={}", item, e);
        }
        return result;
    }

    private String getItems(User user, boolean isUndone) {
        var result = "";
        var mapper = new ObjectMapper();
        List<Item> allItems;
        try {
            if (isUndone) {
                allItems = itemDao.findAllUndone(user);
            } else {
                allItems = itemDao.findAll(user);
            }
            result = mapper.writeValueAsString(allItems);
        } catch (HibernateException e) {
            logger.error("Can't get all items from DB", e);
        } catch (JsonProcessingException e) {
            logger.error("Can't map list of items to json", e);
        }
        return result;
    }
}