<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.Profile" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ProfileDao" %>
<%@ page import="dao.CarDao" %>
<%@ page import="entity.Car" %>
<%@ page import="dao.OrderDao" %>
<%@ page import="entity.Order" %>
<%@ page import="dao.BranchDao" %>
<%@ page import="entity.Branch" %>
<%@ page import="entity.enums.CarLevel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="entity.enums.BodyType" %>
<%@ page import="entity.enums.TransmissionType" %>
<%@ page import="entity.enums.FuelType" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 21.09.2021
  Time: 15:15
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
    String showParam = request.getParameter("show");
    if("customers".equals(showParam)) {
        pageContext.setAttribute("customersParam", showParam);
        ProfileDao profileDao = new ProfileDao();
        List<Profile> profileList = profileDao.getAll();
        request.setAttribute("list", profileList);
    }else if("managers".equals(showParam)) {
        pageContext.setAttribute("managersParam", showParam);
        ProfileDao profileDao = new ProfileDao();
        List<Profile> managerList = profileDao.getAllManagers();
        request.setAttribute("list", managerList);
    }else if("cars".equals(showParam)) {
        pageContext.setAttribute("carsParam", showParam);
        CarDao carDao = new CarDao();
        List<Car> carList = carDao.getAll();
        request.setAttribute("carlvl", new ArrayList<>(Arrays.asList(CarLevel.values())));
        request.setAttribute("bodyTypes", new ArrayList<>(Arrays.asList(BodyType.values())));
        request.setAttribute("transmissionTypes", new ArrayList<>(Arrays.asList(TransmissionType.values())));
        request.setAttribute("fuelTypes", new ArrayList<>(Arrays.asList(FuelType.values())));
        pageContext.setAttribute("branchParam", showParam);
        BranchDao branchDao = new BranchDao();
        List<Branch> branchList = branchDao.getAll();
        request.setAttribute("branchList", branchList);
        request.setAttribute("carList", carList);
    }else if("orders".equals(showParam)) {
        pageContext.setAttribute("ordersParam", showParam);
        OrderDao orderDao = new OrderDao();
        List<Order> orderList = orderDao.getAll();
        request.setAttribute("orderList", orderList);
    }else if("branches".equals(showParam)) {
        pageContext.setAttribute("branchParam", showParam);
        BranchDao branchDao = new BranchDao();
        List<Branch> branchList = branchDao.getAll();
        request.setAttribute("branchList", branchList);
        ProfileDao profileDao = new ProfileDao();
        List<Profile> managerList = profileDao.getAllManagers();
        request.setAttribute("list", managerList);
    }
%>

<main>
    <div class="container-fluid admin-bl">
        <div class="container">
            <div class="page-title">
                <h1>Admin Panel</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-md-2 r-side-pnl">
                <div>
                    <ul>
                        <li>
                            <a href="<%=request.getContextPath()%>/view/adminPage.jsp">Start page</a>
                        </li>
                    </ul>
                </div>
                <hr>
                <ul>
                    <li>
                        <a class="side-menu-item" href="<%=request.getContextPath()%>/view/adminPage.jsp?show=customers">Users</a>
                    </li>
                    <li>
                        <a class="side-menu-item" href="<%=request.getContextPath()%>/view/adminPage.jsp?show=managers">Managers</a>
                    </li>
                    <li>
                        <a class="side-menu-item" href="<%=request.getContextPath()%>/view/adminPage.jsp?show=cars">Cars</a>
                    </li>
                    <li>
                        <a class="side-menu-item"
                           href="<%=request.getContextPath()%>/view/adminPage.jsp?show=orders">Orders</a>
                    </li>
                    <li>
                        <a class="side-menu-item" href="<%=request.getContextPath()%>/view/adminPage.jsp?show=branches">Branches</a>
                    </li>
                </ul>
                <hr>
                <div>
                    <ul>
                        <li>
                            <a href="<%=request.getContextPath()%>/main">Home page</a>
                        </li>
                    </ul>
                </div>
                <hr>
                <div>
                    <ul>
                        <li>
                            <a class="sign-out" href="<%=request.getContextPath()%>/main?signOut=true">Sign out</a>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="col-sm-12 col-md-10 c-pnl">
                <div class="create-btn">
                    <c:choose>
                        <c:when test="${param.show eq 'managers'}">
                            <button id="add-manager" class="slide-toggle btn btn-success">Create new manager</button>
                        </c:when>
                        <c:when test="${param.show eq 'cars'}">
                            <button id="add-car" class="slide-toggle btn btn-success">Create new car</button>
                        </c:when>
                        <c:when test="${param.show eq 'branches'}">
                            <button id="add-branch" class="slide-toggle btn btn-success">Create new branch</button>
                            <button id="add-branch-cancel" class="slide-toggle btn btn-secondary">Cancel</button>
                        </c:when>
                    </c:choose>
                </div>

                <c:choose>
                    <c:when test="${param.show eq 'managers'}">
                        <div class="toggle-edit-bl" style="display: none; margin-bottom: 2rem;">
                            <form action="<%=request.getContextPath()%>/adminPage?formType=createManager" method="post"
                                  autocomplete="off">
                                <div class="row">
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>User login:
                                            <input class="form-control" type="text" name="userLogin" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>User password:</label>
                                        <label>
                                            <input class="form-control editable" type="password" name="userPassword" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>First name:</label>
                                        <label>
                                            <input class="form-control editable" type="text" name="firstName"
                                                   required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Last name:</label>
                                        <label>
                                            <input class="form-control editable" type="text" name="lastName"
                                                   required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Phone number:</label>
                                        <label>
                                            <input class="form-control editable" type="tel" pattern="[+][0-9]{12}" name="phoneNumber"
                                                   placeholder="+380911111111" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Email:</label>
                                        <label>
                                            <input class="form-control editable" type="email" name="userEmail"
                                                   required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-10" style="margin-top: 1rem; margin-bottom: 1rem;">
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:when>

                    <c:when test="${param.show eq 'cars'}">
                        <div class="toggle-edit-bl" style="display: none; margin-bottom: 2rem;">
                            <form action="<%=request.getContextPath()%>/adminPage?formType=createCar" method="post"
                                  enctype="multipart/form-data" autocomplete="off">
                                <div class="row">
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Manufacturer:
                                            <input class="form-control" type="text" name="manufacturer" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Model:
                                            <input class="form-control" type="text" name="model" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Car level:</label>
                                        <label>
                                            <select class="form-control" name="carLvlName">
                                                <c:forEach items="${carlvl}" var="carLvl">
                                                    <option value="${carlvl.get(carLvl.level - 1)}">
                                                        (${carLvl.level})
                                                            ${carlvl.get(carLvl.level - 1)}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Passengers count:
                                            <input class="form-control" type="number" step="1" name="passengers" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Body type:</label>
                                        <label>
                                            <select class="form-control" name="bodyTypeName">
                                                <c:forEach items="${bodyTypes}" var="bodyType">
                                                    <option value="${bodyTypes.get(bodyType.bodyTypeId - 1)}">
                                                        (${bodyType.bodyTypeId})
                                                            ${bodyTypes.get(bodyType.bodyTypeId - 1)}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Transmission type:</label>
                                        <label>
                                            <select class="form-control" name="transmissionTypeName">
                                                <c:forEach items="${transmissionTypes}" var="transmissionType">
                                                    <option value="${transmissionTypes.get(transmissionType.transmissionTypeId - 1)}">
                                                        (${transmissionType.transmissionTypeId})
                                                            ${transmissionTypes.get(transmissionType.transmissionTypeId - 1)}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Fuel type:</label>
                                        <label>
                                            <select class="form-control" name="fuelTypeName">
                                                <c:forEach items="${fuelTypes}" var="fuelType">
                                                    <option value="${fuelTypes.get(fuelType.fuelTypeId - 1)}">
                                                        (${fuelType.fuelTypeId})
                                                            ${fuelTypes.get(fuelType.fuelTypeId - 1)}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Engine:</label>
                                        <label>
                                            <input class="form-control editable" type="number" step="0.01" name="engine" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Fuel consumption:</label>
                                        <label>
                                            <input class="form-control editable" type="number" step="0.01" name="fuelConsumption" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Production year:</label>
                                        <select id="selectElementId" class="form-control" name="prodYear" required></select>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Branch:</label>
                                        <label>
                                            <select class="form-control" name="branchForCarId">
                                                <c:forEach items="${branchList}" var="branchItem">
                                                    <option value="${branchItem.branchId}">
                                                        (${branchItem.branchId})
                                                            ${branchItem.branchName}, ${branchItem.cityName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label>Price:</label>
                                        <label>
                                            <input class="form-control editable" type="number" step="0.01" name="price" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4 cr-input-bl">
                                        <label for="exampleFormControlFile1">Car's photo</label>
                                        <input type="file" class="form-control-file" id="exampleFormControlFile1" name="carImage" required>
                                    </div>
                                    <div class="col-sm-12 col-md-10" style="margin-top: 1rem; margin-bottom: 1rem;">
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:when>

                    <c:when test="${param.show eq 'branches'}">
                        <div class="toggle-edit-bl" style="display: none; margin-bottom: 2rem;">
                            <form action="<%=request.getContextPath()%>/adminPage?formType=createBranch" method="post"
                                  autocomplete="off">
                                <div class="row">
                                    <div class="col-sm-12 col-md-4">
                                        <label>Branch name:
                                            <input class="form-control" type="text" name="branchName" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4">
                                        <label>City:</label>
                                        <label>
                                            <input class="form-control editable" type="text" name="branchCity" required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4">
                                        <label>Address:</label>
                                        <label>
                                            <input class="form-control editable" type="text" name="branchAddress"
                                                   required>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-4">
                                        <label>Manager:</label>
                                        <label>
                                            <select class="form-control" name="managerId">
                                                <c:forEach items="${list}" var="manager">
                                                    <option value="${manager.profileId}">
                                                        (${manager.profileId})
                                                            ${manager.firstName} ${manager.lastName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                    <div class="col-sm-12 col-md-10" style="margin-top: 1rem; margin-bottom: 1rem;">
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:when>
                </c:choose>


                <div class="table-responsive">
                    <table id="myTable" class="table table-hover">
                        <thead>
                        <tr>
                            <c:choose>
                                <c:when test="${param.show eq 'customers' || param.show eq 'managers'}">
                                    <th>USER_ID</th>
                                    <th>USER_LOGIN</th>
                                    <th>USER_ROLE</th>
                                    <th>FRST_NAME</th>
                                    <th>LAST_NAME</th>
                                    <th>PHONE_NUM</th>
                                    <th>EMAIL</th>
                                </c:when>

                                <c:when test="${param.show eq 'cars'}">
                                    <th>CAR_ID</th>
                                    <th>MANUF_NAME</th>
                                    <th>MODEL_NAME</th>
                                    <th>PASSENG_NUM</th>
                                    <th>LVL_NAME</th>
                                    <th>BODY_TYPE_NAME</th>
                                    <th>TRANSM_TYPE_NAME</th>
                                    <th>FUEL_TYPE_NAME</th>
                                    <th>ENGINE</th>
                                    <th>FUEL_CONSUM</th>
                                    <th>PROD_YEAR</th>
                                    <th>BRANCH_NAME</th>
                                    <th>CITY_NAME</th>
                                    <th>ADDRESS</th>
                                    <th>PRICE</th>
                                    <th>ACTIONS</th>
                                </c:when>

                                <c:when test="${param.show eq 'orders'}">
                                    <th>ORDER_ID</th>
                                    <th>ORDER_NUM</th>
                                    <th>PROFILE_ID</th>
                                    <th>FRST_NAME</th>
                                    <th>LAST_NAME</th>
                                    <th>RENT_START</th>
                                    <th>RENT_END</th>
                                    <th>CAR_ID</th>
                                    <th>CAR_NAME</th>
                                    <th>BRANCH_ID</th>
                                    <th>BRANCH_NAME</th>
                                    <th>NEED_DRIVER</th>
                                    <th>TOTAL_COST</th>
                                    <th>ORDER_STATUS</th>
                                </c:when>

                                <c:when test="${param.show eq 'branches'}">
                                    <th>BRANCH_ID</th>
                                    <th>BRANCH_NAME</th>
                                    <th>CITY_NAME</th>
                                    <th>ADDRESS</th>
                                    <th>MANAGER_ID</th>
                                    <th>MANAGER_NAME</th>
                                    <th>ACTIONS</th>
                                </c:when>
                            </c:choose>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${param.show eq 'customers' || param.show eq 'managers'}">
                                <c:forEach items="${list}" var="profile">
                                    <tr>
                                        <td>${profile.user.userId}</td>
                                        <td>${profile.user.userLogin}</td>
                                        <td>${profile.user.userRole}</td>
                                        <td>${profile.firstName}</td>
                                        <td>${profile.lastName}</td>
                                        <td>${profile.phoneNumber}</td>
                                        <td>${profile.email}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>

                            <c:when test="${param.show eq 'cars'}">
                                <c:forEach items="${carList}" var="car">
                                    <tr>
                                        <td>${car.carId}</td>
                                        <td>${car.manufacturer.manufacturerName}</td>
                                        <td>${car.model.modelName}</td>
                                        <td>${car.model.numberOfPassengers}</td>
                                        <td>${car.carLevel}</td>
                                        <td>${car.bodyType}</td>
                                        <td>${car.transmissionType}</td>
                                        <td>${car.fuelType}</td>
                                        <td>${car.engine}</td>
                                        <td>${car.fuelConsumption}</td>
                                        <td>${car.productionYear}</td>
                                        <td>${car.branch.branchName}</td>
                                        <td>${car.branch.cityName}</td>
                                        <td>${car.branch.address}</td>
                                        <td>${car.price}</td>
                                        <td class="text-nowrap">
                                            <a class="btn btn-primary"
                                               href="<%=request.getContextPath()%>/view/updatePage.jsp?update=updateCar&carId=${car.carId}">Update</a>
                                            <a class="btn btn-danger" href="<%=request.getContextPath()%>/adminPage?action=carDelete&carId=${car.carId}">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>

                            <c:when test="${param.show eq 'orders'}">
                                <c:forEach items="${orderList}" var="order">
                                    <tr>
                                        <td>${order.orderId}</td>
                                        <td>${order.orderNumber}</td>
                                        <td>${order.customer.profileId}</td>
                                        <td>${order.customer.firstName}</td>
                                        <td>${order.customer.lastName}</td>
                                        <td>${order.rentStart.time.toLocaleString().replaceAll('0:00:00', '')}</td>
                                        <td>${order.rentEnd.time.toLocaleString().replaceAll('0:00:00', '')}</td>
                                        <td>${order.car.carId}</td>
                                        <td>${order.car.manufacturer.manufacturerName}
                                                ${order.car.model.modelName}
                                        </td>
                                        <td>${order.branch.branchId}</td>
                                        <td>${order.branch.branchName}</td>
                                        <td>${order.needDriver}</td>
                                        <td>${order.totalCost}</td>
                                        <td>${order.status}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>

                            <c:when test="${param.show eq 'branches'}">
                                <c:forEach items="${branchList}" var="branch">
                                    <tr>
                                        <td>${branch.branchId}</td>
                                        <td>${branch.branchName}</td>
                                        <td>${branch.cityName}</td>
                                        <td>${branch.address}</td>
                                        <td>${branch.profile.profileId}</td>
                                        <td>${branch.profile.firstName}
                                                ${branch.profile.lastName}
                                        </td>
                                        <td class="text-nowrap">
                                            <div class="upd-del-btn-bl">
                                                <a class="btn btn-primary"
                                                   href="<%=request.getContextPath()%>/view/updatePage.jsp?update=updateBranch&branchId=${branch.branchId}">Update</a>
                                                <a class="btn btn-danger"
                                                   href="<%=request.getContextPath()%>/adminPage?action=branchDelete&branchId=${branch.branchId}">Delete</a>
                                            </div>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/js/main.js"></script>
<script src="https://kit.fontawesome.com/211c2b523e.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.2/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.11.2/js/jquery.dataTables.min.js"></script>

<script>
    $(document).ready(function () {
        $('#myTable').DataTable();
    });

    $(document).ready(function () {
        $(".slide-toggle").click(function () { // задаем функцию при нажатиии на элемент с классом slide-toggle
            $("div.toggle-edit-bl").slideToggle(); // плавно скрываем, или отображаем все элементы <div>
        });
    });

    const max = new Date().getFullYear(),
        min = max - 50;
        select = document.getElementById('selectElementId');

    for (let i = min; i <= max; i++){
        const opt = document.createElement('option');
        opt.value = i;
        opt.innerHTML = i;
        select.appendChild(opt);
    }
</script>

</body>
</html>
