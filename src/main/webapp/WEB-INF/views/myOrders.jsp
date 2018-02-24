<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.my.orders"/></title>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <link href="/static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <c:choose>
        <c:when test="${orders.size() > 0}">
            <div class="well pre-scrollable">
                <c:forEach items="${orders}" var="order">

                    <div class="well well-sm">
                        <form:form method="POST">
                            <label><spring:message code="label.order.create.time"/></label>
                            <c:out value="${order.orderedAt}"/>

                            <c:forEach items="${order.orderedGoods}" var="good">
                                <div class="well well-sm">
                                    <label><c:out value="${good.key.name}"/></label> &nbsp; &nbsp;
                                    <label><spring:message code="label.price"/></label>
                                    <c:out value="${good.key.price}"/>$ &nbsp; &nbsp;
                                    <label><spring:message code="label.quantity"/></label>
                                    <c:out value="${good.value}"/>
                                </div>
                            </c:forEach>

                            <c:if test="${order.status != 'PAID'}">
                                <div class="row">
                                    <div class="form-actions floatRight">
                                        <input type="hidden" name="orderId" value="${order.id}"/>
                                        <input type="submit" value="<spring:message code='button.pay.order'/>"
                                               class="btn btn-primary btn-sm"/>
                                    </div>
                                </div>
                            </c:if>
                        </form:form>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info lead">
                <spring:message code="alert.no.orders"/>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</div>
</body>
</html>
