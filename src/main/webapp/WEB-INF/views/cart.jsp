<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>

<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <c:choose>
        <c:when test="${order != null}">
            <div class="well pre-scrollable">
                <c:forEach items="${order.orderedGoods}" var="good">
                    <div class="well">
                        <div>
                            <label>Good name:</label>
                            <c:out value="${good.key.name}"/>
                        </div>
                        <div>
                            <label>Good description:</label>
                            <c:out value="${good.key.description}"/>
                        </div>
                        <div>
                            <label>Good price:</label>
                            <c:out value="${good.key.price}"/>
                        </div>
                        <div>
                            <label>Ordered quantity</label>
                            <c:out value="${good.value}"/>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <%--<div>--%>
            <%--<form name="sendOrderForm" method="POST" action="./send_order">--%>
            <%--<fmt:message var="buttonSendOrder" key="button.send.order"/>--%>
            <%--<input type="submit" value="${buttonSendOrder}">--%>
            <%--</form>--%>
            <%--</div>--%>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info lead">
                Sorry, your cart is empty.
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
