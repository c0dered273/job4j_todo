package ru.job4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    private ServletUtils() {
    }

    public static void forwardTo(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Can`t forward to url", e);
        }
    }

    public static void redirectTo(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + url);
        } catch (IOException e) {
            logger.error("Can`t redirect to url", e);
        }
    }
}