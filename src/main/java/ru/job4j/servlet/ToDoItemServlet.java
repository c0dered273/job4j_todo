package ru.job4j.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.util.ItemUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ToDoItemServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ToDoItemServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String json = "";
        if ("getAll".equals(action)) {
            json = ItemUtil.getAllItemsJsonString();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
        }
        try {
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
            logger.error("Servlet writer error", e);
        }
    }
}
