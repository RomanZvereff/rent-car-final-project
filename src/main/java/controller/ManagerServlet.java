package controller;

import dao.OrderDao;
import entity.Order;
import entity.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "ManagerServlet", value = "/managerPage")
public class ManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderDao orderDao = new OrderDao();

        String actionParam = request.getParameter("action");

        if(actionParam != null) {
            long orderIdParam = Long.parseLong(request.getParameter("orderId"));

            Order order = null;
            Optional<Order> optionalOrder = orderDao.get(orderIdParam);
            if(optionalOrder.isPresent()) {
                order = optionalOrder.get();
            }
            if("confirm".equals(actionParam)) {
                orderDao.updateByManager(order, actionParam);
            }else if("reject".equals(actionParam)) {
                orderDao.updateByManager(order, actionParam);
            }else if("cancel".equals(actionParam)) {
                orderDao.updateByManager(order, actionParam);
            }
        }

        response.sendRedirect(getServletContext().getContextPath() + "/view/managerPage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
