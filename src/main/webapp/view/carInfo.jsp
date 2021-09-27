<%@ page import="dao.CarDao" %>
<%@ page import="entity.Car" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 17.09.2021
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
        Car car = null;
        CarDao carDao = new CarDao();
        Optional<Car> optionalCar = carDao.get(Integer.parseInt(request.getParameter("carId")));
        if(optionalCar.isPresent()) {
            car = optionalCar.get();
        }
        session.setAttribute("currentCar", car);
        request.setAttribute("car", car);%>

    <jsp:include page="../templates/header.jsp"/>

    <div class="container car-info-container" style="padding-top: 8rem; min-height: 90vh">
        <div class="row">
            <div class="col-12">
                <h1 class="car-h1-text">${car.manufacturer.manufacturerName} ${car.model.modelName} ${car.productionYear}</h1>
            </div>
            <div class="col-sm-12 col-md-6 car-left-bl">
                <div>
                    <img src="<%=request.getContextPath()%>/templates/images/cars/${car.imageName}" alt="car_image">
                </div>
                <div>
                    <form action="<%=request.getContextPath()%>/carInfo" method="post">
                        <div class="date-params">
                            <div class="dt-param">
                                <label class="rent-input-param">Pick up date</label>
                                <input id="start" type="date" name="startRent" required>
                            </div>
                            <div class="dt-param">
                                <label class="rent-input-param">Drop off date</label>
                                <input id="end" type="date" name="endRent" required>
                            </div>
                        </div>
                        <div class="date-params">
                            <label class="rent-input-param ch-box" for="need-driver">Need driver
                                <input id="need-driver" class="custom-checkbox" type="checkbox" name="needDriver" value="Yes">
                            </label>
                        </div>

                        <input type="hidden" name="carObject" value="<%=car%>">

                        <div class="reserve-btn-bl">
                            <button class="btn btn-success reserve-btn" type="submit">RESERVE</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-sm-12 col-md-6">
                <div>
                    <p class="list-title"> Car information </p>
                    <ul class="info-list">
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/volume.png" alt="lvl-icon">
                            <span> Class: </span>
                            <span class="tech-spec">${car.carLevel}</span>
                        </li>
                        <li class="info-list-item">
                            <i class="fas fa-car-side"></i>
                            <span> Body type: </span>
                            <span class="tech-spec"> ${car.bodyType}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/manual-transmission.png" alt="lvl-icon">
                            <span> Transmission type: </span>
                            <span class="tech-spec"> ${car.transmissionType}</span>
                        </li>
                        <li class="info-list-item">
                            <i class="fas fa-gas-pump"></i>
                            <span> Fuel type</span>
                            <span class="tech-spec"> ${car.fuelType}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/engine.png" alt="lvl-icon">
                            <span> Engine: </span>
                            <span class="tech-spec">${car.engine}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/fuel.png" alt="lvl-icon">
                            <span> Fuel consumption: </span>
                            <span class="tech-spec">${car.fuelConsumption}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/dollar-symbol.png" alt="lvl-icon">
                            <span> Price: </span>
                            <span class="tech-spec price">${car.price} USD/day</span>
                        </li>
                    </ul>

                    <p class="list-title"> Location </p>
                    <ul class="info-list">
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/location-pointer.png" alt="lvl-icon">
                            <span> Branch name: </span>
                            <span class="local-spec">${car.branch.branchName}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/buildings.png" alt="lvl-icon">
                            <span> City: </span>
                            <span class="local-spec">${car.branch.cityName}</span>
                        </li>
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/map.png" alt="lvl-icon">
                            <span> Address: </span>
                            <span class="local-spec">${car.branch.address}</span>
                        </li>
                    </ul>

                    <p class="list-title"> Manager </p>
                    <ul class="info-list">
                        <li class="info-list-item">
                            <img class="info-icon" src="<%=request.getContextPath()%>/templates/images/icons/businessman.png" alt="lvl-icon">
                            <span> Name: </span>
                            <span class="manager-info">${car.branch.profile.firstName}</span>
                            <span class="manager-info">${car.branch.profile.lastName}</span>
                        </li>
                        <li class="info-list-item">
                            <i class="fas fa-phone-alt"></i>
                            <span> Phone number: </span>
                            <span class="manager-info">${car.branch.profile.phoneNumber}</span>
                        </li>
                        <li class="info-list-item">
                            <i class="fas fa-at"></i>
                            <span> Email: </span>
                            <span class="manager-info">${car.branch.profile.email}</span>
                        </li>
                    </ul>
                </div>
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
