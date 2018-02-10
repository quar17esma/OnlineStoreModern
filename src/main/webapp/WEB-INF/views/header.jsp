<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <style>
        .headerButton {
            float: left;
        }
    </style>
    <title>Header</title>
</head>
<body>
<div class="header">
    <h1>Online Store</h1>
<hr/>
<br/>
<div>
    <form class="headerButton" name="logoutForm" method="POST" action="./logout">
        <input type="submit" value="Logout"/>
    </form>
    <form class="headerButton" name="cartForm" method="POST" action="./show_cart">
        <input type="submit" value="Cart">
    </form>
    <form class="headerButton" name="myOrdersForm" method="POST" action="./my_orders">
        <input type="submit" value="My Orders">
    </form>
    <form class="headerButton" name="goodsForm" method="POST" action="./list">
        <input type="submit" value="All Goods">
    </form>
    <br/>
    <hr/>
</div>
</div>
</body>
</html>
