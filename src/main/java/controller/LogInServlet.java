package controller;

import dao.ProfileDao;
import dao.UserDao;
import entity.Profile;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LogInServlet", value = "/logIn")
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession session = req.getSession();
        if(session.getAttribute("customer") != null) {
            resp.sendRedirect(path + "/view/main.jsp");
        }else {
            resp.sendRedirect(path + "/view/logIn.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession oldSession = req.getSession(false);
        if(oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = req.getSession(true);
        String userLogin = req.getParameter("userLogin");
        String userPassword = req.getParameter("userPassword");

        User user;
        UserDao userDao = new UserDao();
        Optional<User> optionalUser = userDao.validateUser(userLogin, userPassword);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            ProfileDao profileDao = new ProfileDao();
            Profile profile = null;
            Optional<Profile> optionalProfile = profileDao.getProfileUserId(user.getUserId());
            if(optionalProfile.isPresent()) {
                profile = optionalProfile.get();
            }
            if(user.getUserRole().equals("ADMIN")) {
                session.setAttribute("customer", profile);
                session.setAttribute("admin", profile);
                resp.sendRedirect(path + "/view/adminPage.jsp");
            }else if(user.getUserRole().equals("MANAGER")) {
                session.setAttribute("customer", profile);
                session.setAttribute("manager", profile);
                resp.sendRedirect(path + "/managerPage");
            }else if(user.getUserRole().equals("CUSTOMER")) {
                session.setAttribute("customer", profile);
                resp.sendRedirect(path + "/view/main.jsp");
            }
        }else {
            session.setAttribute("message", "Invalid username or password");
            resp.sendRedirect(path + "/view/logIn.jsp");
        }
    }

}
