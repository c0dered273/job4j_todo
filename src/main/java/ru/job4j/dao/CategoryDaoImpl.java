package ru.job4j.dao;

import org.hibernate.SessionFactory;
import ru.job4j.model.Category;
import ru.job4j.util.HibernateUtil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public Category findById(long id) {
        return transaction(session -> session.get(Category.class, id), sf);
    }

    @Override
    public List<Category> findAll() {
        return transaction(session -> session.createQuery(
                "from ru.job4j.model.Category c", Category.class
        ).list(), sf);
    }
}
