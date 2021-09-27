<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.Order" %>
<%@ page import="dao.OrderDao" %>
<%@ page import="java.util.List" %><%--
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
    OrderDao orderDao = new OrderDao();
    List<Order> orderList = orderDao.getAll();
    request.setAttribute("list", orderList);
%>

<main>
    <div class="container-fluid">
        <div class="container">
            <div class="page-title">
                <h1>Manager Panel</h1>
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
                                    <c:if test="${(order.status eq 'In processing') || (order.status eq 'Confirmed')}">
                                        <a class="btn btn-success"
                                           href="<%=request.getContextPath()%>/managerPage?action=confirm&orderId=${order.orderId}">Confirm</a>
                                        <a class="btn btn-danger"
                                           href="<%=request.getContextPath()%>/managerPage?action=reject&orderId=${order.orderId}">Reject</a>
                                        <a class="btn btn-primary"
                                           href="<%=request.getContextPath()%>/view/createInvoice.jsp?orderId=${order.orderId}">Car
                                            return</a>
                                    </c:if>
                                </td>
                            </tr>
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
                            <th>ORDER STATUS</th>
                            <th>PAYMENT DETAILS</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${list}" var="order">
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
                                    <c:if test="${(order.status eq 'Processed') || (order.status eq 'Confirmed')}">
                                        <a class="btn btn-success"
                                           href="<%=request.getContextPath()%>/managerPage?action=confirm&orderId=${order.orderId}">Confirm</a>
                                        <a class="btn btn-danger"
                                           href="<%=request.getContextPath()%>/managerPage?action=reject&orderId=${order.orderId}">Reject</a>
                                        <a class="btn btn-primary"
                                           href="<%=request.getContextPath()%>/view/createInvoice.jsp?orderId=${order.orderId}">Car
                                            return</a>
                                    </c:if>
                                </td>
                            </tr>
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
