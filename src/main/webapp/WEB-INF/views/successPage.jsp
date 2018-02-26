<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="title.success.page"/></title>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <link href="/static/css/app.css" rel="stylesheet"/>
</head>
<body>

<div class="generic-container">
    <div class="alert alert-success lead">
        ${success}
    </div>

    <span class="well floatRight">
        <spring:message code="text.go.to"/>
        <a href="./list"><spring:message code="link.goods.list"/></a>
    </span>
</div>

</body>

</html>