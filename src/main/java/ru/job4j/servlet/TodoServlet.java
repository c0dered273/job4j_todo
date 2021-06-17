package ru.job4j.servlet;

import static ru.job4j.util.ServletUtils.INDEX_PAGE;
import static ru.job4j.util.ServletUtils.redirectTo;
import static ru.job4j.util.ServletUtils.setReqEncodingUtf8;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.CategoryDaoImpl;
import ru.job4j.dao.ItemDaoImpl;
import ru.job4j.model.User;
import ru.job4j.service.CategoryService;
import ru.job4j.service.CategoryServiceImpl;
import ru.job4j.service.TodoService;
import ru.job4j.service.TodoServiceImpl;

/**
 * /item.do
 * Обслуживает запросы к задачам
 */
public class TodoServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    /**
     * Отдает списки задач и устанавливает флаг выполнения задачи.
     * Ожидает параметр action и существования в сессии валидного объекта User
     * action=getAll - возвращает все задачи
     * action=getAllUndone - возвращает только невыполненные задачи
     * Для установки флага выполнения задачи требуется параметр id задачи
     * action=setDone - задача выполнена
     * action=setUndone - задача не выполнена
     *
     * @param req req
     * @param resp resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        var user = (User) req.getSession().getAttribute("user");
        var action = req.getParameter("action");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var json = "{\"response\":\"ok\"}";
        TodoService todoService = new TodoServiceImpl(new ItemDaoImpl());
        if ("getAll".equals(action)) {
            json = todoService.getAllItemsJsonString(user);
        }
        if ("getAllUndone".equals(action)) {
            json = todoService.getAllUndoneJsonString(user);
        }
        var id = req.getParameter("id");
        if ("setDone".equals(action) && id != null) {
            try {
                todoService.setDone(Long.parseLong(id), true);
            } catch (NumberFormatException e) {
                logger.error("Id parsing error. Id={}", id);
            }
        }
        if ("setUndone".equals(action) && id != null) {
            try {
                todoService.setDone(Long.parseLong(id), false);
            } catch (NumberFormatException e) {
                logger.error("Id parsing error. Id={}", id);
            }
        }
        try {
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
            logger.error("Servlet writer error", e);
        }
    }

    /**
     * Создает новую задачу.
     *
     * @param req req
     * @param resp resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setReqEncodingUtf8(req);
        var description = req.getParameter("description");
        var user = (User) req.getSession().getAttribute("user");
        var categories = req.getParameterValues("categories");
        if (user == null) {
            redirectTo(INDEX_PAGE, req, resp);
            return;
        }
        TodoService todoService = new TodoServiceImpl(new ItemDaoImpl());
        CategoryService cs = new CategoryServiceImpl(new CategoryDaoImpl());
        todoService.newTask(description, user, cs.getCategoriesFromIdArray(categories));
        redirectTo(INDEX_PAGE, req, resp);
    }
}
