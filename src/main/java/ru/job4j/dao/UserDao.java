package ru.job4j.dao;

import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao {
    void save(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
}
