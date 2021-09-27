<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.OrderDao" %>
<%@ page import="entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 20.09.2021
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Rent car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Roman Zvieriev">
    <meta name="description"
          content="Online car rental service in Ukraine. Rent a different car in Kiev, Dnipro, Odessa...">
    <meta name="keywords" content="rent car, rent Kiev, аренда авто, аренда машин">
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/templates/images/icons/icons_cars.png"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/templates/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/css/main.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/css/special_header.css">
</head>
<body>

<%
    OrderDao orderDao = new OrderDao();
    Order order;
    int counter = 0;
    List<Order> orderList = null;
    String orderIdParam = request.getParameter("orderId");
    String profileIdParam = request.getParameter("profileId");
    if(orderIdParam != null) {
        long orderId = Long.parseLong(orderIdParam);
        Optional<Order> optionalOrder = orderDao.get(orderId);
        if(optionalOrder.isPresent()) {
            order = optionalOrder.get();
            orderList = new ArrayList<>();
            orderList.add(order);
        }
    }
    if(profileIdParam != null) {
        long profileId = Long.parseLong(profileIdParam);
        orderList = orderDao.getAllByUserId(profileId);
    }
    request.setAttribute("list", orderList);%>

<jsp:include page="../templates/header.jsp"/>

<main style="min-height: 90vh; padding-top: 5rem;">
    <div class="container">
        <div class="row">
            <div class="col-12" style="padding-bottom: 2rem;">
                <div class="page-title">
                    <h1>Orders</h1>
                </div>
                <div class="table-responsive tbl-bl">
                    <table class="table table-hover order-table">
                        <thead>
                        <tr>
                            <th nowrap>#</th>
                            <th nowrap>ORDER NUMBER</th>
                            <th nowrap>CAR</th>
                            <th nowrap>PICK-UP DATE</th>
                            <th nowrap>DROP-OFF DATE</th>
                            <th nowrap>NEED DRIVER</th>
                            <th nowrap>BRANCH INFO</th>
                            <th nowrap>MANAGER INFO</th>
                            <th nowrap>TOTAL COST</th>
                            <th nowrap>ORDER STATUS</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="order">
                            <%
                                counter++;
                                request.setAttribute("count", counter);
                            %>
                            <tr>
                                <td style="width: auto">
                                        ${count}
                                </td>
                                <td>
                                        ${order.orderNumber}
                                </td>
                                <td>
                                        ${order.car.manufacturer.manufacturerName} ${order.car.model.modelName}
                                    <br>(${order.car.productionYear})
                                </td>
                                <td>
                                        ${order.rentStart.time.toLocaleString().replaceAll('0:00:00', '')}
                                </td>
                                <td>
                                        ${order.rentEnd.time.toLocaleString().replaceAll('0:00:00', '')}
                                </td>
                                <td>
                                        ${order.needDriver}
                                </td>
                                <td>
                                        ${order.branch.branchName}, <br> ${order.branch.cityName},
                                    <br> ${order.branch.address}
                                </td>
                                <td>
                                        ${order.branch.profile.firstName} ${order.branch.profile.lastName},
                                    <br>${order.branch.profile.phoneNumber}, <br>${order.branch.profile.email}
                                </td>
                                <td>
                                        ${order.totalCost}
                                </td>
                                <td class="order-status" style="font-weight: bold">
                                        ${order.status}
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

<jsp:include page="../templates/footer.jsp"/>

<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/js/main.js"></script>
<script src="https://kit.fontawesome.com/211c2b523e.js" crossorigin="anonymous"></script>

</body>
</html>
