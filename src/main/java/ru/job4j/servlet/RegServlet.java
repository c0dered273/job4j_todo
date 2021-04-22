package ru.job4j.servlet;

import ru.job4j.dao.UserDaoImpl;
import ru.job4j.model.User;
import ru.job4j.util.HashUtil;
import ru.job4j.util.ServletUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegServlet extends HttpServlet {
    private static final String REG_HTML = "reg.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.forwardTo(REG_HTML, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String userName = req.getParameter("username");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        if (userName != null && email != null && pass != null) {
            var passHash = HashUtil.getInstance().getHash(pass);
            var newUser = User.of(userName, email, passHash);
            var userDao = new UserDaoImpl();
            userDao.findByEmail(email)
                    .ifPresentOrElse(
                            usr -> ServletUtils.forwardTo(REG_HTML, req, resp),
                            () -> {
                                userDao.save(newUser);
                                ServletUtils.forwardTo("login.html", req, resp);
                            }
                    );
        }
        ServletUtils.forwardTo(REG_HTML, req, resp);
    }
}
