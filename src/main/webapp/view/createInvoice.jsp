<%@ page import="dao.OrderDao" %>
<%@ page import="entity.Order" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 24.09.2021
  Time: 14:03
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
    long orderId = Long.parseLong(request.getParameter("orderId"));
    OrderDao orderDao = new OrderDao();
    Order order = orderDao.get(orderId).get();
    request.setAttribute("orderItem", order);
%>

<div class="container">
    <div class="row">
        <div class="page-title col-12">
            <h1>Create invoice</h1>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-12" style="margin-bottom: 2rem">
                    <a href="<%=request.getContextPath()%>/managerPage?orderId=${orderItem.orderId}&action=cancel" class="btn btn-info">Accept without invoice</a>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <form action="<%=request.getContextPath()%>/createInvoice" method="post">
                        <div class="row">
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>Order number:
                                    <input class="form-control" type="text" value="${orderItem.orderNumber}" name="orderNumber" readonly>
                                    <input class="form-control" type="hidden" value="${orderItem.orderId}" name="orderId" readonly>
                                </label>
                            </div>
                            <div class="col-12"></div>
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>Client info:
                                    <input class="form-control" type="text"
                                           value="${orderItem.customer.firstName} ${orderItem.customer.lastName}"
                                           readonly>
                                    <input class="form-control" type="hidden" value="${orderItem.customer.profileId}" name="profileId" readonly>
                                </label>
                            </div>
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>Car info:
                                    <input class="form-control" type="text"
                                           value="${orderItem.car.manufacturer.manufacturerName} ${orderItem.car.model.modelName} ${orderItem.car.productionYear}"
                                           readonly>
                                    <input class="form-control" type="hidden" value="${orderItem.car.carId}" name="carId" readonly>
                                </label>
                            </div>
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>IBAN account:
                                    <input class="form-control" type="text" required min="29" max="29" name="accNumber">
                                </label>
                            </div>
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>Amount:
                                    <input class="form-control" type="number" step="0.01" required name="amount">
                                </label>
                            </div>
                            <div class="col-sm-12 col-md-5 cr-input-bl">
                                <label>Damage description:
                                    <textarea class="form-control" type="text" required rows="5" name="damageDesc"></textarea>
                                </label>
                            </div>

                            <div class="col-sm-12 cr-input-bl">
                                <button type="submit" class="btn btn-success">Create invoice</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.2/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.11.2/js/jquery.dataTables.min.js"></script>

</body>
</html>
