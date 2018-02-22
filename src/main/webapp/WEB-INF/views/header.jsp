<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div>
    <h1>Online Store</h1>
    <hr/>

    <c:choose>
        <c:when test="${loggedinuser != null}">
            <div class="well well-lg">
                <span>Dear <strong>${loggedinuser}</strong>, Welcome to Online Store.</span>
                <span class="floatRight"><a href="./logout">Logout</a></span>
            </div>
        </c:when>
    </c:choose>

    <div class="well well-lg">
        <a href="./cart" class="btn custom-width">Cart</a>
        <a href="./my_orders" class="btn custom-width">My Orders</a>
        <a href="./list" class="btn custom-width">All Goods</a>
        <sec:authorize access="hasRole('ADMIN')">
            <a href="./newgood" class="btn custom-width">Add Good</a>
        </sec:authorize>
    </div>
</div>
</body>
</html>
