<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.login.page"/></title>
    <link href="../static/css/bootstrap.css" rel="stylesheet"/>
    <link href="../static/css/app.css" rel="stylesheet"/>
</head>

<body>
<div id="mainWrapper">
    <div>
        Language : <a href="?lang=en_US">English</a> | <a href="?lang=ru_RU">Russian</a>
    </div>
    <div class="login-container">
        <div class="login-card">
            <div class="login-form">
                <c:url var="loginUrl" value="/login"/>
                <form action="${loginUrl}" method="post" class="form-horizontal">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            <p><spring:message code="invalid.username.or.password"/></p>
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            <p><spring:message code="logged.out.success"/></p>
                        </div>
                    </c:if>
                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="username"><i class="fa fa-userMy"></i></label>
                        <input type="text" class="form-control" id="username" name="ssoId"
                               placeholder="<spring:message code='placeholder.enter.username'/>" required>
                    </div>
                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<spring:message code='placeholder.enter.password'/>" required>
                    </div>
                    <div class="input-group input-sm">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="rememberme" name="remember-me">
                                <spring:message code="label.remember.me"/>
                            </label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-actions">
                        <input type="submit"
                               class="btn btn-block btn-primary btn-default"
                               value="<spring:message code='button.log.in'/>">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="well">
        <a href="./newuser"><spring:message code="link.register.now"/></a>
    </div>
</div>

</body>
</html>