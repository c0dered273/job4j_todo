package ru.job4j.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.util.ServletUtils;

/**
 * /index.do
 * Перенаправляет на index.html
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.forwardTo("/index.html", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.forwardTo("/index.html", req, resp);
    }
}
