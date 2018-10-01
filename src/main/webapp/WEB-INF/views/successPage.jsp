<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="title.success.page"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="container">
        <div class="alert alert-success lead">
            ${success}
        </div>

        <div class="well">
            <spring:message code="text.go.to"/>
            <a href="${pageContext.request.contextPath}/goods/list">
                <spring:message code="link.goods.list"/>
            </a>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>
</div>

</body>

</html>