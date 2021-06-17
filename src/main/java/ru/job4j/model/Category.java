package ru.job4j.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Категория задачи.
 */
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    protected Category() {
    }

    /**
     * Возвращает настроенную категорию.
     *
     * @param name название категории
     * @return категория
     */
    public static Category of(String name) {
        var category = new Category();
        category.setName(name);
        return category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
