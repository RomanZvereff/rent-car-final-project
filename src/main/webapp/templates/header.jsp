<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.Profile" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 09.09.2021
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Rent car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <%
        Profile profile = (Profile) session.getAttribute("customer");
        request.setAttribute("customer", profile);
        Profile admin = (Profile) session.getAttribute("admin");
        request.setAttribute("admin", admin);
    %>
    <nav id="mainNav" class="navbar navbar-expand-lg navbar-light fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">Rent car</a>
            <button class="navbar-toggler" onclick="changeColor()" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars" style="font-size: 1.7rem"></i>
            </button>
            <div class="collapse navbar-collapse justify-content-between" id="navbarNav">
                <div class="nav-left text-sm-center">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/main">
                                <i class="fas fa-car"></i> Home
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/templates/contact_us.html">
                                <i class="fas fa-phone"></i> Contact us
                            </a>
                        </li>
                    </ul>
                </div>

                <c:choose>
                    <c:when test="${sessionScope.customer != null}">
                        <div class="nav-right text-sm-center">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <div class="dropdown show">
                                        <a class="nav-link " href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-user"></i> ${customer.firstName}<i class="fas fa-caret-down"></i>
                                        </a>

                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                            <c:if test="${sessionScope.admin != null}">
                                                <a class="dropdown-item " href="<%=request.getContextPath()%>/view/adminPage.jsp">Admin panel</a>
                                            </c:if>
                                            <c:if test="${sessionScope.manager != null}">
                                                <a class="dropdown-item " href="<%=request.getContextPath()%>/view/managerPage.jsp">Manager panel</a>
                                            </c:if>
                                            <a class="dropdown-item " href="<%=request.getContextPath()%>/view/profile.jsp">Profile</a>
                                            <a class="dropdown-item" href="<%=request.getContextPath()%>/view/orderInfo.jsp?profileId=${customer.profileId}">Orders</a>
                                            <a class="dropdown-item sign-out-item" href="<%=request.getContextPath()%>/main?signOut=true">Sign out</a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.customer == null}">
                        <div class="nav-right text-sm-center">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="<%=request.getContextPath()%>/view/registration.jsp">
                                        <i class="fas fa-user-plus"></i> Sign Up
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<%=request.getContextPath()%>/view/logIn.jsp">
                                        <i class="fas fa-sign-in-alt"></i> Sign In
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </nav>

</body>
</html>
