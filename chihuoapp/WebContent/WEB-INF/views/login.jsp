<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="登录" scope="request" />
<jsp:include page="./includes/header.jsp" />

<c:url value="/login" var="loginUrl" />
<form action="${loginUrl}" method="post">
	<c:if test="${param.error != null}">
		<div class="alert alert-error">
			登录失败：
			<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</c:if>
		</div>
	</c:if>
	<c:if test="${param.logout != null}">
		<div class="alert alert-success">您已退出系统</div>
	</c:if>
	<label for="username">用户名</label>
	<input type="text" id="username" name="username" />
	<label for="password">密码</label>
	<input type="password" id="password" name="password" />
	<div class="form-actions">
		<input id="submit" class="btn" name="submit" type="submit" value="登录" />
	</div>
</form>
<jsp:include page="./includes/footer.jsp" />