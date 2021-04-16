package ru.job4j.service;

public interface TodoService {
    String getAllItemsJsonString();
    boolean setDone(long id, boolean flag);
}
