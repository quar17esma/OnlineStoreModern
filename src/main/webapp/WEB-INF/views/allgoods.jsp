<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .headerButton {
            float: left;
        }
    </style>
    <title>All Goods</title>
</head>
<body>
<div class="header">

    <jsp:include page="header.jsp"/>

    <div>
        <c:forEach items="${goods}" var="good">
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
                <form class="button" name="buyNowForm" method="POST" action="./buy_now">
                    <input type="hidden" name="goodId" value="${good.id}">
                    <input type="submit" value="Buy now">
                </form>
            </div>
            <br/>
        </c:forEach>
        <br/>
    </div>
</div>
</body>
</html>
