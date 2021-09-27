<%@ page import="dao.CarDao" %>
<%@ page import="entity.Car" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.UserDao" %>
<%@ page import="entity.Profile" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 16.09.2021
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Rent car</title>
    <meta charset="utf-8">
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
        Profile profile = (Profile) session.getAttribute("customer");
        request.setAttribute("customer", profile);
        %>

    <jsp:include page="../templates/header.jsp"/>

    <div class="container profile-container">
        <div class="row">
            <div class="page-title">
                <h1>Profile</h1>
                <button id="edit-btn" class="btn btn-light edit-btn" onclick="enableForm()">
                    Edit <i class="fas fa-pen"></i>
                </button>
            </div>
            <div class="col-sm-12">

                <form action="<%=request.getContextPath()%>/profile" method="post" autocomplete="off">
                    <div class="row">
                        <div class="col-sm-12 col-md-4">
                            <label>Username:</label>
                            <input class="form-control" type="text" name="userLogin" value="${customer.user.userLogin}" readonly required>
                        </div>
                        <div class="col-md-8"></div>
                        <div class="col-sm-12 col-md-4">
                            <label>First name:</label>
                            <input class="form-control editable" type="text" name="firstName" value="${customer.firstName}" readonly required>
                        </div>
                        <div class="col-sm-12 col-md-4">
                            <label>Last name:</label>
                            <input class="form-control editable" type="text" name="lastName" value="${customer.lastName}" readonly  required>
                        </div>
                        <div class="col-md-4"></div>
                        <div class="col-sm-12 col-md-4">
                            <label>Email:</label>
                            <input class="form-control editable" type="text" name="userEmail" value="${customer.email}" readonly  required>
                        </div>
                        <div class="col-sm-12 col-md-4">
                            <label>Phone number:</label>
                            <input class="form-control editable" type="tel" pattern="[+][0-9]{12}" name="phoneNumber" value="${customer.phoneNumber}" readonly  required>
                        </div>
                        <div class="col-md-4"></div>
                        <div class="col-sm-12 col-md-4 save-btn-bl">
                            <div id="btn-bl">
                                <button id="save-btn" type="submit" class="btn btn-success">Save</button>
                                <button id="cancel-btn" type="button" class="btn btn-secondary" onclick="disableForm()">Cancel</button>
                            </div>
                        </div>
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

        let inputValues = [];

        function enableForm() {
            const inputs = document.getElementsByClassName('editable');
            for (let i = 0; i < inputs.length; ++i) {
                const item = inputs[i];
                inputValues[i] = item.value;
                item.removeAttribute('readonly');
            }
            document.getElementById('btn-bl').style.display='block';
        }

        function disableForm() {
            const inputs = document.getElementsByClassName('editable');
            for (let i = 0; i < inputs.length; ++i) {
                const item = inputs[i];
                if (item.value.length === 0){
                    item.value = inputValues[i];
                }
                item.readOnly = true;
            }
            document.getElementById('btn-bl').style.display='none';
        }

    </script>

</body>
</html>
