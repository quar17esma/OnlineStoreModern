<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Goods</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well pre-scrollable">
        <c:forEach items="${goods}" var="good">
            <div class="well">
                <div class="field">
                    <label>Name:</label>
                    <c:out value="${good.name}"/>
                </div>
                <div class="field">
                    <label>Description:</label>
                    <c:out value="${good.description}"/>
                </div>
                <div class="field">
                    <label>Price:</label>
                    <c:out value="${good.price}"/>
                </div>
                <div class="field">
                    <label>Quantity:</label>
                    <c:out value="${good.quantity}"/>
                </div>
                <div class="field">
                    <a href="<c:url value='/buy-good-${good.id}' />" class="btn btn-success custom-width">Buy</a>
                </div>
            </div>
        </c:forEach>
        <br/>
    </div>
</div>
</body>
</html>
