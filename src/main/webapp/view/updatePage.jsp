<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.BranchDao" %>
<%@ page import="entity.Branch" %>
<%@ page import="java.util.Optional" %>
<%@ page import="dao.ProfileDao" %>
<%@ page import="entity.Profile" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 23.09.2021
  Time: 11:36
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
    String updateParam = request.getParameter("update");
    int branchId = Integer.parseInt(request.getParameter("branchId"));
    if("updateBranch".equals(updateParam)) {
        BranchDao branchDao = new BranchDao();
        Optional<Branch> optionalBranch = branchDao.get(branchId);
        if(optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            session.setAttribute("branchObj", branch);
            ProfileDao profileDao = new ProfileDao();
            List<Profile> profileList = profileDao.getAllManagers();
            request.setAttribute("branch", branch);
            request.setAttribute("list", profileList);
        }
    }
%>

<div class="container">
    <div class="col-12 form-bl">
        <form action="<%=request.getContextPath()%>/adminPage?formType=updateBranch" method="post">
            <c:choose>
                <c:when test="${param.update eq 'updateBranch'}">
                    <div class="row">
                        <div class="col-sm-12 col-md-5">
                            <label>Branch name:
                                <input class="form-control" type="text" name="branchName" required>
                            </label>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <label>City:</label>
                            <label>
                                <input class="form-control editable" type="text" name="branchCity" required>
                            </label>
                        </div>
                        <div class="col-sm-12 col-md-5">
                            <label>Address:</label>
                            <label>
                                <input class="form-control editable" type="text" name="branchAddress"
                                       required>
                                <input type="hidden" name="formType" value="branchForm"/>
                            </label>
                        </div>
                        <div class="col-sm-12 col-md-5">
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
                    </div>
                    <div style="margin-top: 1rem; margin-bottom: 1rem;">
                        <button class="btn btn-success" type="submit">Save</button>
                    </div>
                </c:when>
            </c:choose>
        </form>
    </div>
</div>


<script src="<%=request.getContextPath()%>/templates/vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/templates/js/main.js"></script>
<script src="https://kit.fontawesome.com/211c2b523e.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.2/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.11.2/js/jquery.dataTables.min.js"></script>
</body>
</html>
