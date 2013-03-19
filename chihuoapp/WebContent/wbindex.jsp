<%@page import="weibo4j.model.User"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("loginuser") == null) {
%>
未登录：
<a
	href="https://open.weibo.cn/oauth2/authorize?client_id=1399451403&response_type=code&redirect_uri=http://taochike.sinaapp.com/rest/1/taochike/thirdlogin/weibo"><img
	src="assets/img/weibo/48.png" /></a>

<%
	} else {
		User user = (User) session.getAttribute("loginuser");
		response.getWriter().write("已登录：" + user.toString());
	}
%>
