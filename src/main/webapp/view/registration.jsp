<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 05.09.2021
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

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
    String message = (String) session.getAttribute("loginExists");
    request.setAttribute("loginExists", message);
  %>

  <jsp:include page="../templates/header.jsp"/>
  <div class="limiter">
    <div class="container-login100" style="background-image: url('../templates/images/bg-01.jpg');">
      <div class="wrap-login100">
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/registration" method="post" autocomplete="off">
          <span class="login100-form-title p-b-34 p-t-27" style="margin-bottom: 1.5rem">
                          <i class="fas fa-user-plus"></i> Sign up
                      </span>
          <div class="wrap-input100 validate-input" data-validate="Enter login">
            <input class="input100" type="text" name="userLogin" placeholder="Username" minlength="5" maxlength="20" required>
          </div>
          <div class="invalid-login-bl">
            <c:if test="${loginExists != null}">
              <span id="message" class="">${loginExists}</span>
            </c:if>
          </div>
          <div class="wrap-input100 validate-input" data-validate="Enter password">
            <input id="pass" class="input100" type="password" name="userPassword" placeholder="Password" minlength="8" maxlength="16" required>
          </div>
          <div class="wrap-input100 validate-input" data-validate="Confirm password">
            <input id="confPass" class="input100" type="password" name="confirmPassword" placeholder="Confirm password" minlength="8" maxlength="16" required>
            <span id="cross"></span>
          </div>
          <div class="wrap-input100 validate-input" data-validate="Enter first name">
            <input class="input100" type="text" name="firstName" placeholder="First name" minlength="2" maxlength="30" required>
          </div>
          <div class="wrap-input100 validate-input" data-validate="Enter last name">
            <input class="input100" type="text" name="lastName" placeholder="Last name" minlength="2" maxlength="30" required>
          </div>
          <div class="wrap-input100 validate-input" data-validate="Enter phone number">
            <input id="phone" class="input100" type="tel" name="phoneNumber" pattern="[+][0-9]{12}" placeholder="+380997773300" required >
          </div>
          <div class="wrap-input100 validate-input" data-validate="Enter email">
            <input class="input100" type="email" name="userEmail" placeholder="example@gmail.com" required>
          </div>

          <div class="container-login100-form-btn">
            <button id="submit-btn" class="login100-form-btn" type="submit">
              Register
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
  <script>

    $('#confPass').on('keyup', function () {
      if ($('#pass').val() === $('#confPass').val()) {
        $('#submit-btn').prop('disabled', false);
        $('#confPass').css({'width': '94%', 'display': 'inline-block'});
        $('#cross').html('<i class="fas fa-check"></i>').css({'color': '#3baf0c', 'font-size':'1.1rem'});
      }else{
        $('#submit-btn').prop('disabled', true);
        $('#confPass').css({'width': '94%', 'display': 'inline-block'});
        $('#cross').html('<i class="fas fa-times"></i>').css({'color': '#f54545', 'font-size':'1.1rem'});
      }
    });

  </script>
</body>
</html>
