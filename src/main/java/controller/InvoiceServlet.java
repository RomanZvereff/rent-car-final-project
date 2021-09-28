package controller;

import dao.CarDao;
import dao.InvoiceDao;
import dao.OrderDao;
import dao.ProfileDao;
import entity.Car;
import entity.Invoice;
import entity.Order;
import entity.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "InvoiceServlet", value = "/createInvoice")
public class InvoiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        RequestDispatcher dispatcher = request.getRequestDispatcher(path + "/view/createInvoice.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        long orderId = Long.parseLong(request.getParameter("orderId"));
        long profileId = Long.parseLong(request.getParameter("profileId"));
        int carId = Integer.parseInt(request.getParameter("carId"));
        String accNumber = request.getParameter("accNumber");
        float amount = Float.parseFloat(request.getParameter("amount"));
        String damageDesc = request.getParameter("damageDesc");

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(invoice.generateInvoiceNumber());

        CarDao carDao = new CarDao();
        Optional<Car> optionalCar = carDao.get(carId);
        optionalCar.ifPresent(invoice::setCar);

        OrderDao orderDao = new OrderDao();
        Optional<Order> optionalOrder = orderDao.get(orderId);
        optionalOrder.ifPresent(invoice::setOrder);

        ProfileDao profileDao = new ProfileDao();
        Optional<Profile> optionalProfile = profileDao.get(profileId);
        optionalProfile.ifPresent(invoice::setCustomer);

        invoice.setDescriptionOfDamage(damageDesc);
        invoice.setAccount(accNumber);
        invoice.setAmount(amount);

        InvoiceDao invoiceDao = new InvoiceDao();
        invoiceDao.create(invoice);

        response.sendRedirect(path + "/managerPage");
    }
}
