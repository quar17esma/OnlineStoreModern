<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Buy good</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">

    <jsp:include page="header.jsp"/>

    <div class="well">
        <div>
            <label>Name</label>
            <c:out value="${good.name}"/>
        </div>
        <br/>
        <div>
            <label>Description</label>
            <p><c:out value="${good.description}"/></p>
        </div>
        <br/>
        <div>
            <label>Price</label>
            <c:out value="${good.price}"/>
        </div>
        <br/>
        <form:form method="POST" modelAttribute="good">
            <form:input type="hidden" path="id" id="id" value="${good.id}"/>
            <form:input type="hidden" path="quantity" id="quantity" value="1"/>
            <div class="row">
                <div class="form-actions floatRight">
                    <input type="submit" value="Buy" class="btn btn-primary btn-sm"/>
                    or
                    <a href="./list">Cancel</a>
                </div>
            </div>
        </form:form>
        <br/>
    </div>
</div>
</body>
</html>
