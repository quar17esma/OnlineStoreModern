<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.access.denied"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="generic-container">
    <div class="authbar">
		<span>
			<spring:message code="text.dear"/>
            <strong>${loggedinuser}</strong>
            <spring:message code="text.not.authorized"/>
		</span>
        <span class="floatRight">
			<a href="<c:url value="/logout" />"><spring:message code="link.logout"/>Logout</a>
		</span>
    </div>
</div>
</body>
</html>