package ru.job4j.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Вспомогательный класс для сервлетов.
 */
public class ServletUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);
    public static final String INDEX_PAGE = "/index.do";

    private ServletUtils() {
    }

    /**
     * forwardTo.
     *
     * @param url куда
     * @param req req
     * @param resp resp
     */
    public static void forwardTo(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Can`t forward to url", e);
        }
    }

    /**
     * redirectTo.
     *
     * @param url куда
     * @param req req
     * @param resp resp
     */
    public static void redirectTo(String url, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + url);
        } catch (IOException e) {
            logger.error("Can`t redirect to url", e);
        }
    }

    /**
     * Устанавливает кодировку UTF-8.
     *
     * @param req req
     */
    public static void setReqEncodingUtf8(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("HttpServletRequest set encoding error", e);
        }
    }
}
