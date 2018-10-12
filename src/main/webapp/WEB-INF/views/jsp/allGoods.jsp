<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.all.goods"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="container">
        <div class="row">
            <c:forEach items="${goods}" var="good">
                <div class="col-sm-4">
                    <div class="panel panel-default">
                        <div class="panel-heading"><b><c:out value="${good.name}"/></b></div>
                        <div class="panel-body">
                            <img src="${pageContext.request.contextPath}/goods/imageController/${good.id}"
                                 class="img-responsive" style="width:100%" alt="${good.name}">
                        </div>
                        <div class="panel-footer">
                            <label><spring:message code="label.price"/></label>
                            <c:out value="${good.price}"/>
                            <br>
                            <sec:authorize access="hasRole('ADMIN')">
                                <label><spring:message code="label.quantity"/></label>
                                <c:out value="${good.quantity}"/>
                            </sec:authorize>

                            <a href="<c:url value='../goods/buy-good-${good.id}' />"
                               class="btn btn-success btn-block">
                                <span class="glyphicon glyphicon-shopping-cart"></span> <spring:message
                                    code="button.buy"/>
                            </a>
                            <sec:authorize access="hasRole('ADMIN')">
                                <a href="<c:url value='../goods/edit-good-${good.id}'/>"
                                   class="btn btn-warning btn-block">
                                    <span class="glyphicon glyphicon-pencil"></span> <spring:message
                                        code="button.edit"/>
                                </a>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ADMIN')">
                                <a href="<c:url value='../goods/delete-good-${good.id}'/>"
                                   class="btn btn-danger btn-block">
                                    <span class="glyphicon glyphicon-trash"></span> <spring:message
                                        code="button.delete"/>
                                </a>
                            </sec:authorize>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <br><br>

    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
