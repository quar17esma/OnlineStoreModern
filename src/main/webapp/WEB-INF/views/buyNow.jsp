<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.buy.good"/></title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well">
        <div>
            <h5><b><c:out value="${good.name}"/></b></h5>
        </div>
        <br/>
        <div>
            <label><spring:message code="label.description"/></label>
            <p><c:out value="${good.description}"/></p>
        </div>
        <br/>
        <div>
            <label><spring:message code="label.price"/></label>
            <c:out value="${good.price}"/>
        </div>
        <br/>
        <form:form method="POST" modelAttribute="good">
            <form:input type="hidden" path="id" id="id" value="${good.id}"/>
            <form:input type="hidden" path="name" id="name" value="${good.name}"/>
            <form:input type="hidden" path="description" id="description" value="${good.description}"/>
            <form:input type="hidden" path="price" id="price" value="${good.price}"/>
            <label class="col-md-3 control-lable" for="quantity">
                <spring:message code="label.quantity"/>
            </label>
            <form:input type="text" path="quantity" id="quantity" class="form-control input-sm"/>
            <div class="has-error">
                <form:errors path="quantity" class="help-inline"/>
            </div>
            <div class="row">
                <div class="form-actions floatRight">
                    <input type="submit" value="<spring:message code='button.buy'/>"
                           class="btn btn-primary btn-sm"/>
                    <spring:message code="text.or"/>
                    <a href="./list"><spring:message code="link.cancel"/></a>
                </div>
            </div>
        </form:form>
        <br/>
    </div>
</div>
</body>
</html>
