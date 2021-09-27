package controller;

import dao.OrderDao;
import entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ManagerServlet", value = "/managerPage")
public class ManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionParam = request.getParameter("action");
        long orderIdParam = Long.parseLong(request.getParameter("orderId"));

        Order order = null;
        OrderDao orderDao = new OrderDao();
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
        response.sendRedirect(getServletContext().getContextPath() + "/view/managerPage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
