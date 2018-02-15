<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>
<body>
<div>
    <h1>Online Store</h1>
    <hr/>

    <div class="well well-lg">
        <a href="./logout" class="btn btn-success custom-width">Logout</a>
        <a href="./show_cart" class="btn btn-success custom-width">Cart</a>
        <a href="./my_orders" class="btn btn-success custom-width">My Orders</a>
        <a href="./list" class="btn btn-success custom-width">All Goods</a>
        <a href="./newgood" class="btn btn-success custom-width">Add Good</a>
    </div>
</div>
</body>
</html>
