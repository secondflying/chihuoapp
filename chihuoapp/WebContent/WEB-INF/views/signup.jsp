<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:set var="pageTitle" value="注册" scope="request" />
<jsp:include page="./includes/header.jsp" />

<form:form action="/signup/new" method="post" modelAttribute="signupForm">
	<label>用户名</label>
	<form:input path="userName" name='userName' id="userName" cssClass="input-xlarge" />
	<form:errors path="userName" cssClass="error" />

	<label for="restaurantName">餐厅名</label>
	<form:input path="restaurantName" name='restaurantName' id="restaurantName" cssClass="input-xlarge" />
	<form:errors path="restaurantName" cssClass="error" />

	<label for="password">密码</label>
	<form:password path="password" name='password' id="password" cssClass="input-xlarge" />
	<form:errors path="password" cssClass="error" />

	<label for="password2">确定密码</label>
	<form:password path="password2" name='password2' id="password2" cssClass="input-xlarge" />
	<form:errors path="password2" cssClass="error" />

	<div class="form-actions">
		<input id="submit" class="btn" name="submit" type="submit" value="注册" />
	</div>
</form:form>

<jsp:include page="./includes/footer.jsp" />