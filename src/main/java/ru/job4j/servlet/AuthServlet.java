package ru.job4j.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.UserDaoImpl;
import ru.job4j.model.User;
import ru.job4j.util.HashUtil;
import ru.job4j.util.ServletUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.job4j.util.ServletUtils.*;

public class AuthServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if ("getCurrentUser".equals(action)) {
            returnCurrentUser(req, resp);
            return;
        }
        if ("logout".equals(action)) {
            req.getSession().setAttribute("user", null);
        }
        ServletUtils.forwardTo("/login.html", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email != null && password != null) {
            var user = new UserDaoImpl().findByEmail(email);
            user.ifPresentOrElse(
                    usr -> validateUser(usr, password, req),
                    () -> userNotFound(email)
            );
        } else {
            logger.error("Email or password is null");
        }
       ServletUtils.redirectTo(INDEX_PAGE, req, resp);
    }

    private void validateUser(User user, String password, HttpServletRequest req) {
        var passHash = HashUtil.getInstance().getHash(password);
        if (passHash.equals(user.getPassword())) {
            req.getSession().setAttribute("user", user);
            logger.debug("Success login with email={}", user.getEmail());
            return;
        }
        logger.debug("Wrong password with email={}", user.getEmail());
    }

    private void userNotFound(String email) {
        logger.debug("User with email={} not found", email);
    }

    private void returnCurrentUser(HttpServletRequest req, HttpServletResponse resp) {
        var user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            try {
                var userJson = new ObjectMapper().writeValueAsString(user);
                PrintWriter out = resp.getWriter();
                out.print(userJson);
                out.flush();
            } catch (JsonProcessingException e) {
                logger.error("Can`t process to json, User={}",user, e);
            } catch (IOException e) {
                logger.error("Servlet writer error", e);
            }
        }
    }
}
