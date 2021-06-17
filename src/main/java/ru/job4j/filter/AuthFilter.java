package ru.job4j.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.job4j.util.ServletUtils;

/**
 * Если пользователь не авторизован, перенаправляет на страницу авторизации.
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            ServletUtils.forwardTo("/auth.do", req, resp);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
