<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.my.orders"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <c:choose>
                <c:when test="${orders.size() > 0}">
                    <c:forEach items="${orders}" var="order">
                        <div class="panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <label><spring:message code="label.order.number"/></label>
                                        <a data-toggle="collapse" href="#collapse${order.id}">
                                            <c:out value="${order.id}"/>
                                        </a>&nbsp;&nbsp;
                                        <label><spring:message code="label.order.create.time"/></label>
                                        <c:out value="${localDateTimeFormat.format(order.orderedAt)}"/>
                                    </h4>
                                </div>
                                <div id="collapse${order.id}" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <c:forEach items="${order.orderedGoods}" var="good">
                                            <div class="well well-sm">
                                                <label><c:out value="${good.key.name}"/></label>&nbsp;&nbsp;
                                                <label><spring:message code="label.price"/></label>
                                                <c:out value="${good.key.price}"/>$ &nbsp;&nbsp;
                                                <label><spring:message code="label.quantity"/></label>
                                                <c:out value="${good.value}"/>
                                            </div>
                                        </c:forEach>
                                        <c:if test="${order.status == 'CONFIRMED'}">
                                            <div class="row">
                                                <div class="form-actions floatRight">
                                                    <a href="${pageContext.request.contextPath}/myOrders/pay-${order.id}"
                                                       class="btn btn-success btn-sm">
                                                        <spring:message code='button.pay.order'/>
                                                    </a>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${order.status == 'NEW'}">
                                            <div class="row">
                                                <div class="form-actions floatRight">
                                                    <a href="${pageContext.request.contextPath}/myOrders/confirm-${order.id}"
                                                       class="btn btn-success btn-sm">
                                                        <spring:message code='button.confirm.order'/>
                                                    </a>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info lead">
                        <spring:message code="alert.no.orders"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</div>
</body>
</html>
