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
    <title>All Goods</title>
</head>
<body>
<div class="header">
    <h1>Online Store</h1>
    <hr/>

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
        <form class="headerButton" name="goodsForm" method="POST" action="./show_goods">
            <input type="submit" value="All Goods">
        </form>

        <%--&lt;%&ndash;For ADMIN&ndash;%&gt;--%>
        <%--<c:if test="${sessionScope.client.user.role == 'ADMIN'}">--%>
        <%--<form class="headerButton" name="addNewGoodForm" method="POST" action="./edit_good">--%>
        <%--<fmt:message var="buttonAddGood" key="button.add.good"/>--%>
        <%--<input type="submit" value="${buttonAddGood}">--%>
        <%--</form>--%>
        <%--<form class="headerButton" name="manageClientsForm" method="POST" action="./manage_clients">--%>
        <%--<fmt:message var="buttonManageClients" key="button.manage.clients"/>--%>
        <%--<input type="submit" value="${buttonManageClients}">--%>
        <%--</form>--%>
        <%--</c:if>--%>
        <%--<br/>--%>
        <%--<hr/>--%>
    </div>
    </br>
    </br>

    <div>
        <c:forEach items="${goods}" var="good">
            <div class="field">
                <label>Name:</label>
                <c:out value="${good.name}"/>
            </div>
            <div class="field">
                <label>Description:</label>
                <br/>
                <c:out value="${good.description}"/>
            </div>
            <div class="field">
                <label>Price:</label>
                <ctg:price price="${good.price}"/>
            </div>
            <div class="field">
                <form class="button" name="buyNowForm" method="POST" action="./buy_now">
                    <input type="hidden" name="goodId" value="${good.id}">
                        <%--<fmt:message var="buyButton" key="button.buy.now"/>--%>
                    <input type="submit" value="Buy now">
                </form>
            </div>

            <%--&lt;%&ndash;For ADMIN&ndash;%&gt;--%>
            <%--<c:if test="${sessionScope.client.user.role == 'ADMIN'}">--%>
            <%--<form class="button" name="goToEditGoodForm" method="POST" action="./edit_good">--%>
            <%--<input type="hidden" name="goodId" value="${good.id}">--%>
            <%--<fmt:message var="buttonEdit" key="button.edit"/>--%>
            <%--<input type="submit" value="${buttonEdit}">--%>
            <%--</form>--%>
            <%--<form class="button" name="deleteGoodForm" method="POST" action="./delete_good">--%>
            <%--<input type="hidden" name="goodId" value="${good.id}">--%>
            <%--<fmt:message var="buttonDelete" key="button.delete"/>--%>
            <%--<input type="submit" value="${buttonDelete}">--%>
            <%--</form>--%>
            <%--</c:if>--%>
            <%--<br/>--%>
            <%--<hr/>--%>
        </c:forEach>
        <br/>
    </div>

    <%--<div>--%>
    <%--<c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">--%>
    <%--<form class="button" name="goodsPagesForm" method="POST" action="./show_goods">--%>
    <%--<input type="hidden" name="page" value="${loop.count}">--%>
    <%--<input type="submit" value="${loop.count}">--%>
    <%--</form>--%>
    <%--</c:forEach>--%>
    <%--</div>--%>
</div>
</body>
</html>
