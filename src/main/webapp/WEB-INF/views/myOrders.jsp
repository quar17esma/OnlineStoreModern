<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <link href="/static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well pre-scrollable">
        <c:forEach items="${orders}" var="order">

            <div class="well well-sm">
                <form:form method="POST">
                    <label>Order creation time:</label>
                    <c:out value="${order.orderedAt}"/>

                    <c:forEach items="${order.orderedGoods}" var="good">
                        <div class="well well-sm">
                            <label><c:out value="${good.key.name}"/></label> &nbsp; &nbsp;
                            <label>  Price:</label>
                            <c:out value="${good.key.price}"/>$ &nbsp; &nbsp;
                            <label>  Quantity:</label>
                            <c:out value="${good.value}"/>
                        </div>
                    </c:forEach>

                    <c:if test="${order.status != 'PAID'}">
                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <input type="submit" value="Pay order" class="btn btn-primary btn-sm"/>
                            </div>
                        </div>
                    </c:if>
                </form:form>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
