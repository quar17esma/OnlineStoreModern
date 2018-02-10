<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List of Goods</title>
</head>

<body>
<h2>List of Goods</h2>
<table>
    <tr>
        <td>Name</td>
        <td>Description</td>
        <td>Price</td>
        <td>Quantity</td>
    </tr>
    <c:forEach items="${goods}" var="good">
        <tr>
            <td>${good.name}</td>
            <td>${good.description}</td>
            <td>${good.price}</td>
            <td>${good.quantity}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
