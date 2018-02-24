<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.all.goods"/></title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well pre-scrollable">
        <c:forEach items="${goods}" var="good">
            <div class="well">
                <div>
                    <h5><b><c:out value="${good.name}"/></b></h5>
                </div>
                <div>
                    <label><spring:message code="label.description"/></label>
                    <c:out value="${good.description}"/>
                </div>
                <div>
                    <label><spring:message code="label.price"/></label>
                    <c:out value="${good.price}"/>
                </div>
                <div>
                    <label><spring:message code="label.quantity"/></label>
                    <c:out value="${good.quantity}"/>
                </div>
                <div>
                    <a href="<c:url value='/buy-good-${good.id}' />" class="btn btn-success custom-width">
                        <spring:message code="button.buy"/>
                    </a>
                </div>
            </div>
        </c:forEach>
        <br/>
    </div>
</div>
</body>
</html>
