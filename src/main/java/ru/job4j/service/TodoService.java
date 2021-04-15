package ru.job4j.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.ItemDao;
import ru.job4j.model.Item;

import java.util.List;

public class TodoService {
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final ItemDao itemDao;

    public TodoService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public String getAllItemsJsonString() {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        List<Item> allItems;
        try {
            allItems = itemDao.findAll();
            result = mapper.writeValueAsString(allItems);
        } catch (HibernateException e) {
            logger.error("Can't get all items from DB", e);
        } catch (JsonProcessingException e) {
            logger.error("Can't map list of items to json", e);
        }
        return result;
    }
}
