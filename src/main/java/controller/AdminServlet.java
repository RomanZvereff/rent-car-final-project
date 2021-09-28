package controller;

import dao.*;
import entity.*;
import entity.enums.UserRole;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Optional;

@WebServlet(name = "AdminServlet", value = "/adminPage")
@MultipartConfig
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null) {
            response.sendRedirect("/view/logIn.jsp");
        }
        RequestDispatcher dispatcher;
        String adminPath = path + "/view/adminPage.jsp";

        String actionType = request.getParameter("action");

        switch(actionType) {
            case "branchDelete":
                deleteBranch(request, response);
                adminPath = path + "/view/adminPage.jsp?show=branches";
                break;
            case "carDelete":
                deleteCar(request, response);
                adminPath = path + "/view/adminPage.jsp?show=cars";
                break;
        }

        response.sendRedirect(adminPath);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        RequestDispatcher dispatcher;

        String adminPath = path + "/view/adminPage.jsp";

        String formType = request.getParameter("formType");

        switch(formType) {
            case "createBranch":
                createBranch(request, response);
                adminPath = path + "/view/adminPage.jsp?show=branches";
                break;
            case "updateBranch":
                updateBranch(request, response);
                adminPath = path + "/view/adminPage.jsp?show=branches";
                break;
            case "createManager":
                createManager(request, response);
                adminPath = path + "/view/adminPage.jsp?show=managers";
                break;
            case "createCar":
                createCar(request, response);
                adminPath = path + "/view/adminPage.jsp?show=cars";
                break;
            case "updateCar":
                updateCar(request, response);
                adminPath = path + "/view/adminPage.jsp?show=cars";
                break;
        }
        response.sendRedirect(adminPath);

    }

    private void createBranch(HttpServletRequest request, HttpServletResponse response) {
        Branch branch = new Branch();
        branch.setBranchName(request.getParameter("branchName"));
        branch.setCityName(request.getParameter("branchCity"));
        branch.setAddress(request.getParameter("branchAddress"));

        ProfileDao profileDao = new ProfileDao();
        long managerId = Long.parseLong(request.getParameter("managerId"));
        Optional<Profile> optionalProfile = profileDao.get(managerId);
        optionalProfile.ifPresent(branch::setProfile);

        BranchDao branchDao = new BranchDao();
        branchDao.create(branch);
    }

    private void updateBranch(HttpServletRequest request, HttpServletResponse response) {
        Branch branch = (Branch) request.getSession().getAttribute("branchObj");
        String branchName = request.getParameter("branchName");
        String branchCity = request.getParameter("branchCity");
        String branchAddress = request.getParameter("branchAddress");
        String managerId = request.getParameter("managerId");
        String[] params = {branchName, branchCity, branchAddress, managerId};
        BranchDao branchDao = new BranchDao();
        branchDao.update(branch, params);
    }

    private void deleteBranch(HttpServletRequest request, HttpServletResponse response) {
        int branchId = Integer.parseInt(request.getParameter("branchId"));
        BranchDao branchDao = new BranchDao();
        branchDao.delete(branchId);
    }

    private void createManager(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserLogin(request.getParameter("userLogin"));
        user.setUserPassword(request.getParameter("userPassword"));
        user.setUserRole(String.valueOf(UserRole.MANAGER));
        long userId = userDao.create(user);
        user.setUserId(userId);

        ProfileDao profileDao = new ProfileDao();
        Profile profile = new Profile();
        profile.setFirstName(request.getParameter("firstName"));
        profile.setLastName(request.getParameter("lastName"));
        profile.setPhoneNumber(request.getParameter("phoneNumber"));
        profile.setEmail(request.getParameter("userEmail"));
        profile.setUser(user);
        long profileId = profileDao.create(profile);
        profile.setProfileId(profileId);
    }

    private void createCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarDao carDao = new CarDao();
        Car car = new Car();

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerName(request.getParameter("manufacturer").toUpperCase(Locale.ROOT));
        ManufacturerDao manufacturerDao = new ManufacturerDao();
        long manufacturerId = manufacturerDao.create(manufacturer);
        manufacturer.setManufacturerId((int) manufacturerId);
        car.setManufacturer(manufacturer);

        Model model = new Model();
        model.setManufacturer(manufacturer);
        model.setModelName(request.getParameter("model").toUpperCase(Locale.ROOT));
        model.setNumberOfPassengers(Integer.parseInt(request.getParameter("passengers")));
        ModelDao modelDao = new ModelDao();
        long modelId = modelDao.create(model);
        model.setModelId((int) modelId);
        car.setModel(model);

        car.setCarLevel(request.getParameter("carLvlName"));
        car.setBodyType(request.getParameter("bodyTypeName"));
        car.setTransmissionType(request.getParameter("transmissionTypeName"));
        car.setFuelType(request.getParameter("fuelTypeName"));
        car.setEngine(Float.parseFloat(request.getParameter("engine")));
        car.setFuelConsumption(Float.parseFloat(request.getParameter("fuelConsumption")));
        car.setProductionYear(Integer.parseInt(request.getParameter("prodYear")));

        BranchDao branchDao = new BranchDao();
        Branch branch = null;
        Optional<Branch> optionalBranch = branchDao.get(Integer.parseInt(request.getParameter("branchForCarId")));
        if(optionalBranch.isPresent()) {
            branch = optionalBranch.get();
        }
        car.setBranch(branch);
        car.setPrice(Integer.parseInt(request.getParameter("price")));

        Part filePart = request.getPart("carImage");
        String fileName = filePart.getSubmittedFileName();

        car.setImageName(fileName);
        carDao.create(car);

        uploadCarImage(request, response, filePart);
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response) {
        Car car = (Car) request.getSession().getAttribute("carObj");
        String carLvl = request.getParameter("carLvlName");
        String carEngine = request.getParameter("carEngine");
        String fuelConsumption = request.getParameter("fuelConsumption");
        String prodYear = request.getParameter("prodYear");
        String carPrice = request.getParameter("carPrice");
        String[] params = {carLvl, carEngine, fuelConsumption, prodYear, carPrice};
        CarDao carDao = new CarDao();
        carDao.update(car, params);
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response) {
        int carId = Integer.parseInt(request.getParameter("carId"));
        CarDao carDao = new CarDao();
        carDao.delete(carId);
    }

    public void uploadCarImage(HttpServletRequest request, HttpServletResponse response, Part filePart) {
        String path = getServletContext().getRealPath("/templates/images/cars" + File.separator + filePart.getSubmittedFileName());

        try {
            InputStream inputStream = filePart.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            int bytesRead;
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, bytesRead);
            }
            fileOutputStream.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
