<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.Car" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.CarDao" %>
<%@ page session="true" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    CarDao carDao = new CarDao();
    List<Car> carList = carDao.getAll();
    request.setAttribute("list", carList);
%>

<main class="main-bl">

    <jsp:include page="../templates/header.jsp"/>

    <div class="container-fluid preview-bl">
        <div class="row">
            <div class="col-sm-12 col-md-1 col-lg-2"></div>

            <div class="col-sm-12 col-md-10 col-lg-8 main-header-text">
                <h1>Movement that gives emotions</h1>
                <p class="prev-sub-text">Over 10 years of experience. During our existence, we have significantly
                    improved our service. It is no surprise that the number of our clients is increasing every year.</p>
            </div>
            <div class="col-sm-12 col-md-1 col-lg-2">
            </div>
        </div>
    </div>

    <div class="arrow-down">
        <i class="fas fa-angle-down"></i>
    </div>

</main>

<section>
    <div class="s2-header-text">
        <h2 class="s2-text">
            Find your perfect vehicle
        </h2>
    </div>
</section>

<div class="container sort-container">
    <div class="row sort-bl">
        <div class="col-6 text-left filter-menu">
            <a class="nav-link " href="#" role="button" id="dropdownFilterMenu" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false" style="display: inline;">
                <i class="fas fa-filter"></i><span class="filter-text">  Filter </span><i class="fas fa-caret-down"></i>
            </a>
            <span id="drop-filtered"><span class="filtered"> drop filter </span><i class="far fa-times-circle"></i></span>
            <div class="dropdown-menu filter-mn" aria-labelledby="dropdownFilterMenu">
                <span class="filter-list">car class: </span>
                <button id="econom" class="dropdown-item filter-menu-item">econom</button>
                <button id="standard" class="dropdown-item filter-menu-item">standard</button>
                <button id="lux" class="dropdown-item filter-menu-item">lux</button>
            </div>
        </div>
        <div class="col-6 text-right">
            <span class="sort-text">Sort by class: </span>
            <button id="ascOrder"><i class="fas fa-sort-amount-down-alt"></i></button>
            <button id="descOrder"><i class="fas fa-sort-amount-up-alt"></i></button>
        </div>
    </div>
</div>


<div class="container">
    <div id="car-list" class="row car-container">
        <c:forEach items="${list}" var="car">
            <div class="car-item flex-item col-lg-4 col-md-6 col-sm-12 ${car.carLevel}" data-sort-in="${car.carLvlId}">
                <div id="car-bl-item">
                    <div class="car-name-item">
                        <p class="car-name">
                                ${car.manufacturer.manufacturerName}
                                ${car.model.modelName}
                            (${car.productionYear})
                        </p>
                    </div>
                    <div class="car-img">
                        <img src="<%=request.getContextPath()%>/templates/images/cars/${car.imageName}" alt="car_image">
                    </div>
                    <div class="car-info">
                        <ul class="car-info-list">
                            <li class="info-item">
                                <i class="fas fa-coins"></i> ${car.carLevel}
                            </li>
                            <li class="info-item">
                                <i class="fas fa-gas-pump"></i> ${car.fuelType}
                            </li>
                            <li class="info-item">
                                <i class="fas fa-tachometer-alt"></i> ${car.engine}
                            </li>
                        </ul>
                        <div class="car-cur-price">
                            <i class="fas fa-money-bill-wave"></i> ${car.price}
                            <span>USD/day</span>
                        </div>
                    </div>
                    <div class="car-btn">
                        <c:choose>
                            <c:when test="${sessionScope.customer != null}">
                                <a class="btn btn-warning rent-btn"
                                   href="<%=request.getContextPath()%>/view/carInfo.jsp?carId=${car.carId}">
                                    RESERVE
                                </a>
                            </c:when>
                            <c:when test="${sessionScope.customer == null}">
                                <a class="btn btn-warning rent-btn" href="<%=request.getContextPath()%>/view/logIn.jsp">
                                    RESERVE
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../templates/footer.jsp"/>

<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/js/main.js"></script>
<script src="https://kit.fontawesome.com/211c2b523e.js" crossorigin="anonymous"></script>

<script>
    const $divs = $("div.car-item");

    $('#descOrder').on('click', function () {
        const opOrder = $divs.sort(function (a, b) {
            return $(a).data('sort-in') < $(b).data('sort-in') ? 1 : -1;
        });
        $("#car-list").html(opOrder);

    });

    $('#ascOrder').on('click', function () {
        const inOrder = $divs.sort(function (a, b) {
            return $(a).data('sort-in') < $(b).data('sort-in') ? -1 : 1;
        });
        $("#car-list").html(inOrder);

    });

    var $btns = $('.filter-menu-item').click(function () {
        var $el = $('.' + this.id).fadeIn(450);
        $('#car-list > div').not($el).hide();
        $('#drop-filtered').css('display', 'inline-block');
    })

    $('#drop-filtered').click(function() {
        $('#car-list > div').fadeIn(450);
        $('#drop-filtered').css('display', 'none');
    })



</script>

</body>
</html>
