package controller;

import dao.ProfileDao;
import dao.UserDao;
import entity.Profile;
import entity.User;
import entity.enums.UserRole;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/rent_car/view/registration.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = req.getParameter("userLogin");

        UserDao userDao = new UserDao();
        if(userDao.getUserIfExists(userLogin)) {
            session.setAttribute("loginExists", "Login already exists");
            doGet(req, resp);
        }else {
            User user = new User();
            user.setUserLogin(req.getParameter("userLogin"));
            user.setUserPassword(req.getParameter("userPassword"));
            user.setUserRole(String.valueOf(UserRole.CUSTOMER));
            long userId = userDao.create(user);
            user.setUserId(userId);

            ProfileDao profileDao = new ProfileDao();
            Profile profile = new Profile();
            profile.setFirstName(req.getParameter("firstName"));
            profile.setLastName(req.getParameter("lastName"));
            profile.setPhoneNumber(req.getParameter("phoneNumber"));
            profile.setEmail(req.getParameter("userEmail"));
            profile.setUser(user);
            long profileId = profileDao.create(profile);
            profile.setProfileId(profileId);

            session.setAttribute("customer", profile);
            resp.sendRedirect(getServletContext().getContextPath() + "/view/main.jsp");
        }
    }

}
