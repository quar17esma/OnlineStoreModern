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

    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <c:choose>
                <c:when test="${order != null}">
                    <form:form method="POST">
                            <c:forEach items="${order.orderedGoods}" var="good">
                                <div class="well">
                                    <div>
                                        <h5><b><c:out value="${good.key.name}"/></b></h5>
                                    </div>
                                    <div>
                                        <label><spring:message code="label.description"/></label>
                                        <c:out value="${good.key.description}"/>
                                    </div>
                                    <div>
                                        <label><spring:message code="label.price"/></label>
                                        <c:out value="${good.key.price}"/>
                                    </div>
                                    <div>
                                        <label><spring:message code="label.ordered.quantity"/></label>
                                        <c:out value="${good.value}"/>
                                    </div>
                                </div>
                            </c:forEach>

                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit" value="<spring:message code='button.confirm.order'/>"
                                       class="btn btn-primary btn-sm"/>
                            </div>
                        </div>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info lead">
                        <spring:message code="empty.cart"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
