<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.my.cart"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="container">
        <c:choose>
            <c:when test="${order != null}">
                <div class="well">
                    <c:forEach items="${order.orderedGoods}" var="good">
                        <div class="row">
                            <div class="col-md-12">
                                <h2><b><c:out value="${good.key.name}"/></b></h2><br>
                            </div>
                            <div class="col-md-3">
                                <img src="${pageContext.request.contextPath}/goods/imageController/${good.key.id}"
                                     class="img-thumbnail" alt="${good.key.name}">
                            </div>
                            <div class="col-md-9">
                                <div class="row">
                                    <label><spring:message code="label.quantity"/></label>
                                    <c:out value="${good.value}"/>
                                </div>
                                <div class="row">
                                    <label><spring:message code="label.price"/></label>
                                    <c:out value="${good.key.price}"/>
                                </div>
                                <div class="row">
                                    <label><spring:message code="label.description"/></label>
                                    <p><c:out value="${good.key.description}"/></p><br>
                                </div>
                            </div>
                            <br>
                        </div>
                        <br>
                    </c:forEach>
                    <div class="row">
                        <div class="col-md-12">
                            <a href="${pageContext.request.contextPath}/orders/cart/confirm"
                               class="btn btn-success">
                                <span class="glyphicon glyphicon-ok"></span>
                                <spring:message code='button.confirm.order'/>
                            </a>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info lead">
                    <spring:message code="empty.cart"/>
                </div>
                <br><br>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="footer.jsp"/>
</div>

</body>
</html>
