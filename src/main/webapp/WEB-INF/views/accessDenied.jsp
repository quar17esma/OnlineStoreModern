<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AccessDenied page</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div class="generic-container">
    <div class="authbar">
		<span>
			Dear <strong>${loggedinuser}</strong>, You are not authorized to access this page.
		</span>
        <span class="floatRight">
			<a href="<c:url value="/logout" />">Logout</a>
		</span>
    </div>
</div>
</body>
</html>