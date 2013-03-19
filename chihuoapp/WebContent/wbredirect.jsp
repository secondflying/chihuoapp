<%@page import="weibo4j.model.WeiboException"%>
<%@page import="weibo4j.model.User"%>
<%@page import="weibo4j.Users"%>
<%@page import="weibo4j.http.AccessToken"%>
<%@page import="weibo4j.Oauth"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (!StringUtils.isEmpty(request.getParameter("code"))) {
		String code = request.getParameter("code");

		response.getWriter().write(code);
		response.getWriter().write("\n");

		//根据code获取token
		Oauth oauth = new Oauth();
		AccessToken at = oauth.getAccessTokenByCode(code);
		
		response.getWriter().write(at.toString());
		response.getWriter().write("\n");

		Users um = new Users();
		um.client.setToken(at.getAccessToken());
		try {
		        User user = um.showUserById(at.getUid());
		        session.setAttribute("loginuser", user);
		        response.getWriter().write(user.toString());
		        
		        //判断该用户是否在本地库中，如果没有新增，有的话更新token和expire
		        
		        //response.sendRedirect("wbindex.jsp");
		        
		} catch (WeiboException e) {
		        e.printStackTrace();
		}

	} else if (!StringUtils.isEmpty(request.getParameter("error"))) {
		response.getWriter().write(
				"授权错误：" + request.getParameter("error_description"));
	}
%>
