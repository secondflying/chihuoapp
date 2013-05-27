<%@page import="java.util.Enumeration"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	Enumeration<String> s = session.getAttributeNames();
	while (s.hasMoreElements()) {
		String name = s.nextElement();
		response.getWriter().write(
				"session '" + name + "': "
						+ session.getAttribute(name).toString());

	}
	response.getWriter().write("session '" + 33 + "': " + 44);
%>
