<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String URL = "jdbc:mysql://sqld.duapp.com:4050/dOiqSXJRgvZDTVTWFzIA";//
	String Username = "81Cw3Mww8yIuHcfcASjqd3cz";
	String Password = "pQrAeNWMTA3DXqAPv1dU2IpTOU5zd7mr";
	
	String URL2 = "jdbc:mysql://192.168.1.102:3306/DinnerPlatform";//
	String Username2 = "root";
	String Password2 = "root";
	
	String Driver = "com.mysql.jdbc.Driver";

	String sql = "SELECT * FROM recipe";
	response.getWriter().write("结果:");

	try {
		Class.forName(Driver).newInstance();
		Connection conn = DriverManager.getConnection(URL,Username,Password);
		//Connection conn = DriverManager.getConnection(URL2,Username2,Password2);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String lastName = rs.getString("name");
			response.getWriter().write(lastName);
		}
		conn.close();
	} catch (Exception e) {
		response.getWriter().write("Got an exception! ");
	}
%>
