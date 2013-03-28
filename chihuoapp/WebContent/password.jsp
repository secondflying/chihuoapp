<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>密码生成器</title>
<script type="text/javascript">
	function test() {
		window.location.href = "password.jsp?n="
				+ document.getElementById('pwdTxt').value;
	}
</script>
</head>
<body>
	<%
		String num = request.getParameter("n");
		response.getWriter()
				.print("密码个数：<input id='pwdTxt' type='text' value='" + num + "'><input type='button' value='生成' onclick='test();''><BR />");
		Set<String> set = new HashSet<String>();
		if (!StringUtils.isBlank(num)) {
			try {
				int n = Integer.parseInt(num);
				String base = "ACDEFGHJKMNPQRSTUVWXYZ2345679";
				Random random = new Random();
				int count = 0;
				while (true) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < 6; i++) {
						int number = random.nextInt(base.length());
						sb.append(base.charAt(number));
					}
					String psd = sb.toString();

					if (!set.contains(psd)) {
						count++;
						response.getWriter().print(psd);
						response.getWriter().print("<BR >");
					}
					if (count == n) {
						break;
					}
				}

			} catch (Exception err) {
				response.getWriter().print("请输入数字！！");
			}
		}
	%>
</body>
</html>
