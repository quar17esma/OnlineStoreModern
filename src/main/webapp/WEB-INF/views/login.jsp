<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.login.page"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <br>
    <div class="row">
        <div class="btn-group">
            <a href="?lang=en_US" class="btn btn-info" role="button">
                <spring:message code="link.english"/>
            </a>
            <a href="?lang=ru_RU" class="btn btn-info" role="button">
                <spring:message code="link.russian"/>
            </a>
        </div>
    </div>
    <div class="row">
        <c:if test="${successRegistrationMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successRegistrationMessage}"/>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                <spring:message code="logged.out.success"/>
            </div>
        </c:if>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <p><spring:message code="invalid.username.or.password"/></p>
            </div>
        </c:if>
    </div>
    <div class="row">
        <h2><spring:message code="title.login"/></h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <c:url var="loginUrl" value="/login"/>
                <form name="loginForm" method="POST" action="${loginUrl}">
                    <div class="form-group">
                        <div class="input-group">
                            <label class="input-group-addon" for="username">
                                <span class="glyphicon glyphicon-user"/>
                            </label>
                            <input class="form-control" type="email" name="ssoId" value="" id="username"
                                   placeholder="<spring:message code='placeholder.enter.email'/>"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${errorNoSuchUser}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <label class="input-group-addon" for="username">
                                <span class="glyphicon glyphicon-lock"/>
                            </label>
                            <input class="form-control" type="password" name="password" value="" id="password"
                                   placeholder="<spring:message code='placeholder.enter.password'/>"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${errorWrongPassword}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="rememberme" name="remember-me">
                                <spring:message code="label.remember.me"/>
                            </label>
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <input type="submit" class="btn btn-default" value="<spring:message code='button.log.in'/>">
                        <input type="reset" class="btn btn-default" value="<spring:message code='button.reset'/>">
                    </div>
                </form>
            </div>
        </div>
        <hr/>
        <a href="./new_user"><spring:message code="link.register"/></a>
    </div>
</div>
</body>
</html>