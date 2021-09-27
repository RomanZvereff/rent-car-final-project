<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 05.09.2021
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <title>Rent car</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="author" content="Roman Zvieriev">
  <meta name="description" content="Online car rental service in Ukraine. Rent a different car in Kiev, Dnipro, Odessa...">
  <meta name="keywords" content="rent car, rent Kiev, аренда авто, аренда машин">
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/templates/images/icons/icons_cars.png"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/templates/vendor/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/css/main.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/css/special_header.css">
</head>
<body>
  <%
    String message = (String) session.getAttribute("message");
    request.setAttribute("message", message);
  %>

  <jsp:include page="../templates/header.jsp"/>
  <div class="limiter">
    <div class="container-login100" style="background-image: url('../templates/images/sing_in_bg.jpg');">
      <div class="wrap-login100">
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/logIn" method="post" autocomplete="off">
          <span class="login100-form-title p-b-34 p-t-27" style="margin-bottom: 1.5rem">
                        <i class="fas fa-sign-in-alt"></i>  Sign In
                      </span>

          <div class="wrap-input100 validate-input" data-validate = "Enter username">
            <input class="input100" type="text" name="userLogin" placeholder="Username" minlength="5" maxlength="20" required>
          </div>

          <div class="wrap-input100 validate-input" data-validate="Enter password">
            <input class="input100" type="password" name="userPassword" placeholder="Password" minlength="8" maxlength="16" required>
          </div>
          <div class="invalid-msg-bl">
            <c:if test="${message != null}">
              <span id="message" class="invalid-login">${message}</span>
            </c:if>
          </div>

          <div class="container-login100-form-btn">
            <button class="login100-form-btn" type="submit">
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <jsp:include page="../templates/footer.jsp"/>

  <script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath()%>/templates/js/main.js"></script>
  <script src="https://kit.fontawesome.com/211c2b523e.js" crossorigin="anonymous"></script>

</body>
</html>
