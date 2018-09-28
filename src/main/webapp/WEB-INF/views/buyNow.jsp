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
                <div>
                    <h5><b><c:out value="${good.name}"/></b></h5>
                </div>
                <br/>
                <div>
                    <label><spring:message code="label.description"/></label>
                    <p><c:out value="${good.description}"/></p>
                </div>
                <br/>
                <div>
                    <label><spring:message code="label.price"/></label>
                    <c:out value="${good.price}"/>
                </div>
                <br/>
                <form:form method="POST">
                    <label class="col-md-3 control-label" for="orderedQuantity">
                        <spring:message code="label.quantity"/>
                    </label>
                    <div class="col-md-2">
                        <input type="number" min="1" value="1"
                               name="orderedQuantity" id="orderedQuantity" required="required"
                               class="form-control input-sm"/>
                        <div class="has-error">
                            <form:errors path="quantity" class="help-inline"/>
                        </div>
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
                </form:form>
                <br/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
