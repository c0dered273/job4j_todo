package ru.job4j.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.CategoryDaoImpl;
import ru.job4j.service.CategoryServiceImpl;

/**
 * /categories.do
 * Обслуживает запросы к категориям
 */
public class CategoryServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServlet.class);

    /**
     * Возвращает массов с категориями задач.
     *
     * @param req req
     * @param resp resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var categoryService = new CategoryServiceImpl(new CategoryDaoImpl());
        var json = categoryService.getAllCategoriesJsonString();
        try {
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
            logger.error("Servlet writer error", e);
        }
    }
}
