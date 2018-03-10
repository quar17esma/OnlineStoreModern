<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.header"/></title>
</head>
<body>
<div class="row">
    <div class="col-md-10 col-md-offset-1">
        <div class="text-center">
            <h3><spring:message code="label.online.store"/></h3>
        </div>
        <c:choose>
            <c:when test="${sessionScope.get('loggedInUser') != null}">
                <div class="well well-sm">
                <span>
                    <spring:message code="text.dear"/>
                    <strong>${sessionScope.get('loggedInUser')}</strong>
                    <spring:message code="text.welcome.to.store"/>
                </span>
                    <span class="floatRight">
                    <a href="./logout">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <spring:message code="link.logout"/>
                    </a>
                </span>
                </div>
            </c:when>
        </c:choose>

        <nav class="navbar navbar-default">
            <ul class="nav navbar-nav">
                <li><a href="./cart" class="btn"><spring:message code="link.cart"/></a></li>
                <li><a href="./myOrders" class="btn"><spring:message code="link.my.orders"/></a></li>
                <li><a href="./list" class="btn"><spring:message code="link.goods.list"/></a></li>
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="./new-good" class="btn"><spring:message code="link.add.good"/></a></li>
                </sec:authorize>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
