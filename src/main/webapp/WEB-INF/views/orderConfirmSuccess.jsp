<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order Confirmation Success Page</title>
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <link href="/static/css/app.css" rel="stylesheet"/>
</head>
<body>

<div class="generic-container">
    <div class="alert alert-success lead">
        ${success}
    </div>

    <span class="well floatRight">
        Go to <a href="./list">Goods List</a>
    </span>
</div>

</body>

</html>