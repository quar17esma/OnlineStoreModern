<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.buy.good"/></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <c:if test="${errorNotEnoughGood != null}">
            <div class="alert alert-danger">
                <c:out value="${errorNotEnoughGood}"/>
            </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="well">
                <form:form method="POST" class="form-inline">
                    <div class="row">
                        <div class="col-md-4">
                            <h2><b><c:out value="${good.name}"/></b></h2><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}/goods/imageController/${good.id}"
                                 class="img-responsive" style="width:100%" alt="${good.name}">
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <label class="control-label" for="orderedQuantity">
                                    <spring:message code="label.quantity"/>
                                </label>
                                <input type="number" min="1" value="1"
                                       name="orderedQuantity" id="orderedQuantity" required="required"
                                       class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="quantity" class="help-inline"/>
                                </div>
                            </div>
                            <br><br>
                            <div class="row">
                                <label><spring:message code="label.price"/></label>
                                <c:out value="${good.price}"/>
                            </div>
                            <div class="row">
                                <label><spring:message code="label.description"/></label>
                                <p><c:out value="${good.description}"/></p>
                                <br>
                            </div>
                            <div class="row">
                                <div class="form-actions floatRight">
                                    <input type="submit" value="<spring:message code='button.buy'/>"
                                           class="btn btn-primary btn-sm"/>
                                    <spring:message code="text.or"/>
                                    <a href="${pageContext.request.contextPath}/goods/list"><spring:message
                                            code="link.cancel"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
                <br/>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
