<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="title.edit.good"/></title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">

    <jsp:include page="header.jsp"/>

    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div class="well">
                <form:form method="POST" modelAttribute="good">
                    <form:input type="hidden" path="id" id="id"/>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="name">
                                <spring:message code="label.name"/>
                            </label>
                            <div class="col-md-7">
                                <form:input type="text" path="name" id="name"
                                            value="${good.name}" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="name" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="description">
                                <spring:message code="label.description"/>
                            </label>
                            <div class="col-md-7">
                                <form:textarea type="text" path="description" id="description"
                                               value="${good.description}" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="description" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="price">
                                <spring:message code="label.price"/>
                            </label>
                            <div class="col-md-2">
                                <form:input type="number" path="price" id="price"
                                            value="${good.price}" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="price" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-label" for="quantity">
                                <spring:message code="label.quantity"/>
                            </label>
                            <div class="col-md-2">
                                <form:input type="number" path="quantity" id="quantity"
                                            value="${good.quantity}" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="quantity" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="<spring:message code='button.confirm'/>"
                                   class="btn btn-primary btn-sm"/>
                            <spring:message code="text.or"/>
                            <a href="./list"><spring:message code="link.cancel"/></a>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
