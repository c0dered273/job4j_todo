package ru.job4j.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Инициализирует и создает SessionFactory, экземпляр один на весь проект.
 */
public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sf;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure().build();
            try {
                sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                logger.error("Session factory creation failed", e);
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sf;
    }
}
