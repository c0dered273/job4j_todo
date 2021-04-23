package ru.job4j.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.UserDaoImpl;
import ru.job4j.model.User;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email != null || password != null) {
            var user = new UserDaoImpl().findByEmail(email);
            user.ifPresentOrElse(
                    usr -> checkUserPass(usr, password, req),
                    () -> userNotFound(email)
            );
        } else {
            logger.error("Email or password is null");
        }
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            logger.error("Can't send redirect to index.html", e);
        }
    }

    private void checkUserPass(User user, String password, HttpServletRequest req) {
        if (password.equals(user.getPassword())) {
            req.getSession().setAttribute("user", user);
            logger.debug("Success login with email={}", user.getEmail());
            return;
        }
        logger.debug("Wrong password with email={}", user.getEmail());
    }

    private void userNotFound(String email) {
        logger.debug("User with email={} not found", email);
    }

}
