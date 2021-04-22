package ru.job4j.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.ItemDaoImpl;
import ru.job4j.model.User;
import ru.job4j.service.TodoService;
import ru.job4j.service.TodoServiceImpl;
import ru.job4j.util.ServletUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TodoServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        var user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        var json = "{\"response\":\"ok\"}";
        TodoService todoService = new TodoServiceImpl(new ItemDaoImpl());
        if (user == null) {
            ServletUtils.redirectTo("/", req, resp);
            return;
        }
        if ("getAll".equals(action)) {
            json = todoService.getAllItemsJsonString(user);
        }
        if ("getAllUndone".equals(action)) {
            json = todoService.getAllUndoneJsonString(user);
        }
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String description = req.getParameter("description");
        var user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            ServletUtils.redirectTo("/", req, resp);
            return;
        }
        TodoService todoService = new TodoServiceImpl(new ItemDaoImpl());
        todoService.newTask(description, user);
        ServletUtils.redirectTo("/", req, resp);
    }
}
