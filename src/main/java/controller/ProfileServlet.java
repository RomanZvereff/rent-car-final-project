package controller;

import dao.ProfileDao;
import entity.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        ProfileDao profileDao = new ProfileDao();
        HttpSession session = req.getSession();
        Profile profile = (Profile) session.getAttribute("customer");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userEmail = req.getParameter("userEmail");
        String phoneNumber = req.getParameter("phoneNumber");
        String[] params = {firstName, lastName, userEmail, phoneNumber};

        profileDao.update(profile, params);
        Optional<Profile> optionalProfile = profileDao.get(profile.getProfileId());
        if(optionalProfile.isPresent()) {
            Profile newProfile = optionalProfile.get();
            newProfile.setUser(profile.getUser());
            session.setAttribute("customer", newProfile);
        }
        resp.sendRedirect(path + "/view/profile.jsp");
    }
}
