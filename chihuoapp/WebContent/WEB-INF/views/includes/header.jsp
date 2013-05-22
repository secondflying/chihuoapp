<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="${pageTitle}" /></title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<c:url var="cssUrl" value="/assets/bootstrap/css/bootstrap.css" />
<link href="${cssUrl}" rel="stylesheet" />
<c:url var="cssUrl2" value="/assets/css/css.css" />
<link href="${cssUrl2}" rel="stylesheet" />

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
          <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
</head>
<body>
	<div id="nav-bar" class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<c:url var="welcomeUrl" value="/" />
				<a class="brand" href="${welcomeUrl}">管理平台</a>
				<div class="nav-collapse">
					<ul class="nav">
						<c:url var="infoUrl" value="/shop/info" />
						<li><a id="infoLink" href="${infoUrl}">餐厅信息</a></li>
						<c:url var="recipeUrl" value="/shop/recipe" />
						<li><a id="recipeLink" href="${recipeUrl}">菜品维护</a></li>
						<c:url var="deskUrl" value="/shop/desk" />
						<li><a id="deskLink" href="${deskUrl}">餐桌维护</a></li>
						<c:url var="orderUrl" value="/shop/order" />
						<li><a id="orderLink" href="${orderUrl}">订单管理</a></li>
						<c:url var="waiterUrl" value="/shop/waiter" />
						<li><a id="waiterLink" href="${waiterUrl}">服务员管理</a></li>
					</ul>
				</div>
				<div id="nav-account" class="nav-collapse pull-right">
					<ul class="nav">
						<sec:authorize access="authenticated" var="authenticated" />
						<c:choose>
							<c:when test="${authenticated}">
								<li><div>你好:<sec:authentication property="principal.name" /></div></li>
								<c:url var="logoutUrl" value="/logout" />
								<li><a id="navLogoutLink" href="${logoutUrl}">退出系统</a></li>
							</c:when>
							<c:otherwise>
								<c:url var="signupUrl" value="/signup/form" />
								<li><a id="navSignupLink" href="${signupUrl}">注册</a></li>
								<c:url var="loginUrl" value="/login/form" />
								<li><a id="navLoginLink" href="${loginUrl}">登录</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>

	</div>

	<div class="container">
		<c:if test="${message != null}">
			<div class="alert alert-success" id="message">
				<c:out value="${message}" />
			</div>
		</c:if>
		<h1 id="title">
			<c:out value="${pageTitle}" />
		</h1>