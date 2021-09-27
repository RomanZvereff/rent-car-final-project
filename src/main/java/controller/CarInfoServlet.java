package controller;

import dao.OrderDao;
import entity.Branch;
import entity.Car;
import entity.Order;
import entity.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

@WebServlet(name = "CarInfoServlet", value = "/carInfo")
public class CarInfoServlet extends HttpServlet {

    private static final String ORDER_STATUS = "In processing";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession session = req.getSession();
        if(session.getAttribute("customer") == null || session.isNew()) {
            resp.sendRedirect(path + "/view/main.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = req.getSession();
        Profile profile = (Profile) session.getAttribute("customer");
        Car car = (Car) session.getAttribute("currentCar");

        Order order = new Order();
        order.setOrderNumber(Order.getSaltString());
        order.setCustomer(profile);
        try {
            calendarStart.setTime(dateFormat.parse(req.getParameter("startRent")));
            order.setRentStart(calendarStart);

            calendarEnd.setTime(dateFormat.parse(req.getParameter("endRent")));
            order.setRentEnd(calendarEnd);
        }catch(ParseException e) {
            e.printStackTrace();
        }

        order.setCar(car);
        Branch branch = new Branch();
        branch.setBranchId(car.getBranch().getBranchId());
        order.setBranch(branch);
        if("Yes".equals(req.getParameter("needDriver"))) {
            order.setNeedDriver("Yes");
        }else{
            order.setNeedDriver("No");
        }
        int days = (int) Duration.between(order.getRentStart().toInstant(), order.getRentEnd().toInstant()).toDays();

        order.setTotalCost(car.getPrice() * (days + 1));
        order.setStatus(ORDER_STATUS);

        OrderDao orderDao = new OrderDao();
        long orderId = orderDao.create(order);
        resp.sendRedirect(path + "/view/orderInfo.jsp?orderId=" + orderId);
    }

}
