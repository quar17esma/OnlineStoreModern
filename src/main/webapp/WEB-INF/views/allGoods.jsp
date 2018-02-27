<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.all.goods"/></title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well pre-scrollable">
        <c:forEach items="${goods}" var="good">
            <div class="well col-md-6">
                <div class="row">
                    <div class="col-md-12">
                        <h5><b><c:out value="${good.name}"/></b></h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div>
                            <label><spring:message code="label.price"/></label>
                            <c:out value="${good.price}"/>
                        </div>
                        <div>
                            <label><spring:message code="label.quantity"/></label>
                            <c:out value="${good.quantity}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="row">
                            <a href="<c:url value='/buy-good-${good.id}' />" class="btn btn-success floatRight">
                                <spring:message code="button.buy"/>
                            </a>
                        </div>
                        <sec:authorize access="hasRole('ADMIN')">
                            <div class="row">
                                <a href="<c:url value='/edit-good-${good.id}' />" class="btn btn-warning floatRight">
                                    <spring:message code="button.edit"/>
                                </a>
                            </div>
                        </sec:authorize>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <a href="#desc${good.id}" data-toggle="collapse">
                            <spring:message code="label.description"/>
                        </a>
                        <br/>
                        <br/>
                        <div id="desc${good.id}" class="collapse">
                            <c:out value="${good.description}"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <br/>
    </div>
</div>
</body>
</html>
