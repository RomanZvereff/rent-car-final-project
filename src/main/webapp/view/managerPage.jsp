<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.Order" %>
<%@ page import="dao.OrderDao" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Profile" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.InvoiceDao" %>
<%@ page import="entity.Invoice" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 24.09.2021
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rent car</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Roman Zvieriev">
    <meta name="description"
          content="Online car rental service in Ukraine. Rent a different car in Kiev, Dnipro, Odessa...">
    <meta name="keywords" content="rent car, rent Kiev, аренда авто, аренда машин">
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/templates/images/icons/icons_cars.png"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/templates/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/css/main.css">
</head>
<body>

<%
    Profile profile = (Profile) session.getAttribute("manager");
    long profileId = profile.getProfileId();

    OrderDao orderDao = new OrderDao();
    List<Order> orderList = orderDao.getAll();
    orderList = orderList.stream().filter(o -> o.getBranch().getProfile().getProfileId() == profileId).collect(Collectors.toList());

    InvoiceDao invoiceDao = new InvoiceDao();
    List<Invoice> invoiceList = invoiceDao.getAll();

    request.setAttribute("profileId", profileId);
    request.setAttribute("list", orderList);
    request.setAttribute("invoices", invoiceList);
%>

<main>
    <div class="container-fluid">
        <div class="container">
            <div class="d-flex">
                <div class="container">
                    <div class="page-title">
                        <h1>Manager Panel</h1>
                    </div>
                </div>
                <div class="container text-right" style="padding-top: 2rem;">
                    <a class="sign-out" href="<%=request.getContextPath()%>/main?signOut=true" style="color: #ff9898; font-size: 1.3rem;">Sign out</a>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div>
                    <h1 style="color: #55548b; font-size: 2rem;">Orders:</h1>
                </div>
                <div class="table-responsive">
                    <table id="myTable" class="table table-hover">
                        <thead>
                        <tr>
                            <th>ORDER NUMBER</th>
                            <th>CUSTOMER NAME</th>
                            <th>RENT START</th>
                            <th>RENT END</th>
                            <th>CAR NAME</th>
                            <th>BRANCH NAME</th>
                            <th>NEED DRIVER</th>
                            <th>TOTAL COST</th>
                            <th>ORDER STATUS</th>
                            <th>ACTIONS</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${list}" var="order">
                            <c:if test="${profileId == order.branch.profile.profileId}">
                                <tr>
                                    <td>${order.orderNumber}</td>
                                    <td>
                                            ${order.customer.firstName} ${order.customer.lastName}
                                    </td>
                                    <td>${order.rentStart.time.toLocaleString().replaceAll('0:00:00', '')}</td>
                                    <td>${order.rentEnd.time.toLocaleString().replaceAll('0:00:00', '')}</td>
                                    <td>
                                            ${order.car.manufacturer.manufacturerName}
                                            ${order.car.model.modelName}
                                    </td>
                                    <td>${order.branch.branchName}</td>
                                    <td>${order.needDriver}</td>
                                    <td>${order.totalCost}</td>
                                    <td>${order.status}</td>
                                    <td class="text-nowrap">
                                        <c:choose>
                                            <c:when test="${(order.status eq 'In processing')}">
                                                <a class="btn btn-success"
                                                   href="<%=request.getContextPath()%>/managerPage?action=confirm&orderId=${order.orderId}">Confirm</a>
                                                <a class="btn btn-danger"
                                                   href="<%=request.getContextPath()%>/managerPage?action=reject&orderId=${order.orderId}">Reject</a>
                                            </c:when>
                                            <c:when test="${(order.status eq 'Confirmed')}">
                                                <a class="btn btn-primary"
                                                   href="<%=request.getContextPath()%>/view/createInvoice.jsp?orderId=${order.orderId}">Car
                                                    return</a>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <hr>
                <div>
                    <h1 style="color: #55548b; font-size: 2rem;">Invoices:</h1>
                </div>
                <div class="table-responsive">
                    <table class="myTable table table-hover">
                        <thead>
                        <tr>
                            <th>INVOICE NUMBER</th>
                            <th>CUSTOMER NAME</th>
                            <th>ORDER NUMBER</th>
                            <th>DAMAGE DESCRIPTION</th>
                            <th>AMOUNT</th>
                            <th>IBAN ACCOUNT</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${invoices}" var="invoice">
                            <c:forEach items="${list}" var="order">
                                <c:if test="${invoice.order.orderId == order.orderId}">
                                    <tr>
                                        <td>${invoice.invoiceNumber}</td>
                                        <td>
                                                ${invoice.customer.firstName} ${invoice.customer.lastName}
                                        </td>
                                        <td>${invoice.order.orderNumber}</td>
                                        <td>${invoice.descriptionOfDamage}</td>
                                        <td>${invoice.amount}</td>
                                        <td>${invoice.account}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.2/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.11.2/js/jquery.dataTables.min.js"></script>

<script>

    $(document).ready(function () {
        $('.myTable').DataTable();
    });

</script>

</body>
</html>
