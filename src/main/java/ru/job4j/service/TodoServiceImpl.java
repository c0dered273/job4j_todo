package ru.job4j.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.ItemDao;
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;

/**
 * Реализует работу с задачами.
 */
public class TodoServiceImpl implements TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);
    private final ItemDao itemDao;

    public TodoServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * Возвращает все задачи, принадлежащие указанному пользователю в виде json строки.
     *
     * @param user кому принадлежат задачи
     * @return json строка массив объектов Item
     */
    @Override
    public String getAllItemsJsonString(User user) {
        return getItems(user, false);
    }

    /**
     * Возвращает все незавершенные задачи, принадлежащие указанному пользователю
     * в виде json строки.
     *
     * @param user кому принадлежат задачи
     * @return json строка массив объектов Item
     */
    @Override
    public String getAllUndoneJsonString(User user) {
        return getItems(user, true);
    }

    /**
     * Устанавливает флаг выполнения задачи.
     *
     * @param id long id задачи
     * @param flag true - задача завершена
     * @return результат изменения состояния задачи
     */
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

    /**
     * Создает новую задачу.
     *
     * @param description описание задачи
     * @param user кто создал
     * @param categories список категорий задачи
     * @return результат выполнения
     */
    @Override
    public boolean newTask(String description, User user, List<Category> categories) {
        var result = false;
        if (categories.isEmpty()) {
            return false;
        }
        var item = Item.of(
                description, new Date(System.currentTimeMillis()), false, user, categories
        );
        try {
            itemDao.save(item);
            result = true;
        } catch (Exception e) {
            logger.error("Can't save new item. Item={}", item, e);
        }
        return result;
    }

    /**
     * Возвращает json строку с массивом задач.
     * Если флаг isUndone установлен в true, метод возвращает только невыполненные задачи.
     *
     * @param user кому принадлежат задачи
     * @param isUndone true - незавершенные задачи, false - все задачи
     * @return json строка с массивом объектов
     */
    private String getItems(User user, boolean isUndone) {
        var result = "";
        List<Item> allItems;
        try {
            if (isUndone) {
                allItems = itemDao.findAllUndone(user);
            } else {
                allItems = itemDao.findAll(user);
            }
            result = new ObjectMapper().writeValueAsString(allItems);
        } catch (HibernateException e) {
            logger.error("Can't get all items from DB", e);
        } catch (JsonProcessingException e) {
            logger.error("Can't map list of items to json", e);
        }
        return result;
    }
}