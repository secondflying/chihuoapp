<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="${pageTitle}" /></title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<c:url var="cssUrl" value="/assets/bootstrap/css/bootstrap.css" />
<link href="${cssUrl}" rel="stylesheet" />
<c:url var="cssUrl2" value="/assets/jquery/plugin/DataTables/css/jquery.dataTables.css" />
<link href="${cssUrl2}" rel="stylesheet" />
<c:url var="cssUrl3" value="/assets/css/css.css" />
<link href="${cssUrl3}" rel="stylesheet" />

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
          <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

<jsp:include page="js.jsp" />
</head>
<body>
	<div id="nav-bar" class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<c:url var="welcomeUrl" value="/" />
				<a class="brand" href="${welcomeUrl}">管理平台</a>
				<div class="nav-collapse">
					<ul class="nav">

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/info')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="infoUrl" value="/shop/info" />
						<li class="${cssClass}"><a id="infoLink" href="${infoUrl}">餐厅信息</a></li>

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/recipe')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="recipeUrl" value="/shop/recipe" />
						<li class="${cssClass}"><a id="recipeLink" href="${recipeUrl}">菜品维护</a></li>

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/desk')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="deskUrl" value="/shop/desk" />
						<li class="${cssClass}"><a id="deskLink" href="${deskUrl}">餐桌维护</a></li>

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/order')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="orderUrl" value="/shop/order" />
						<li class="${cssClass}"><a id="orderLink" href="${orderUrl}">订单管理</a></li>

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/waiter')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="waiterUrl" value="/shop/waiter" />
						<li class="${cssClass}"><a id="waiterLink" href="${waiterUrl}">服务员管理</a></li>

						<c:set var="cssClass" value=" " />
						<c:if test="${fn:endsWith(requestScope['javax.servlet.forward.servlet_path'], '/shop/report')}">
							<c:set var="cssClass" value="active" />
						</c:if>
						<c:url var="reportUrl" value="/shop/report" />
						<li class="${cssClass}"><a id="reportLink" href="${reportUrl}">营业报表</a></li>

					</ul>
				</div>
				<div id="nav-account" class="nav-collapse pull-right">
					<ul class="nav">
						<sec:authorize access="authenticated" var="authenticated" />
						<c:choose>
							<c:when test="${authenticated}">
								<li><div>
										你好:
										<sec:authentication property="principal.name" />
									</div></li>
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