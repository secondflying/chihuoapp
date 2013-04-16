<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<meta property="wb:webmaster" content="a8a9d252dc035657" />
<meta property="qc:admins" content="2641272317301356316110063757" />
<title><tiles:getAsString name="title" /></title>

<link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="assets/css/css.css" rel="stylesheet" media="screen">

</head>
<body>
	<tiles:insertAttribute name="top" />

	<div class="home">
		<div class="container">
			<!-- <header class="hero-unit">
				<h1>
					淘吃客<small>史上最大的点餐平台</small>
				</h1>
				<p>
					<a class="btn btn-success btn-large" href="#signup-modal"
						data-toggle="modal">免费注册</a>
				</p>
			</header> -->
		</div>
	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>

</body>
</html>
