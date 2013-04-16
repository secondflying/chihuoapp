<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container">
	<div class="">
		<ul class="nav nav-pills pull-right" id="login-div">
			<li><a href="#login-modal" data-toggle="modal" id="login-a">登录</a></li>
			<li><a href="#signup-modal" data-toggle="modal" id="register-a">注册</a></li>
		</ul>

		<div class="btn-group  pull-right" id="user-div" style="display: none">
			<button class="btn" id="user-btn">u2</button>
			<button class="btn  btn-info dropdown-toggle" data-toggle="dropdown">
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="infoManager">管理平台</a></li>
				<li><a href="#">个人资料</a></li>
				<li><a href="#" id="logout-a">退出</a></li>
			</ul>
		</div>
		<h2 class="muted"><a href="index">淘吃客</a></h2>
	</div>
</div>

<div class="container">
	<div class="modal hide fade" id="login-modal">
		<!-- <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">登录到淘吃客</h3>
		</div> -->
		<form class="form-horizontal" id="login-form"
			enctype="application/x-www-form-urlencoded" method="post">
			<fieldset>
				<div class="input-prepend">
					<span class="add-on">
						<i class="icon-user"></i>
					</span>
					<input type="text" placeholder="用户名 或 email" name="username"
						maxlength="30" id="idusername">
				</div>
				<div class="input-prepend">
					<span class="add-on">
						<i class="icon-lock"></i>
					</span>
					<input type="password"
					placeholder="密码" name="password" id="idpassword">
				</div>
				 	<input type="hidden" name="utype" id="utype" value="2" />
					<button id="login-btn" class="button1">登 录</button>
			</fieldset>
			<div class="squaredFour">
				<input type="checkbox" value="None" id="squaredFour" name="check">
				<label for="squaredFour"></label>
			</div>
		</form>
		<!-- <a href="/accounts/password/reset/"><span>忘记密码?</span></a> · <a
			href="#signup-modal"><span>注册账号</span></a> -->
	</div>

	<div class="modal hide fade" id="signup-modal">
		<!-- <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">免费注册</h3>
		</div>
 -->		<form class="form-horizontal" id="register-form"
			enctype="application/x-www-form-urlencoded" method="post">
			<fieldset>
				<div class="input-prepend">
					<span class="add-on">
						<i class="icon-user"></i>
					</span>
					<input type="text" placeholder="用户名 或 email" name="username"
						maxlength="30" id="idusername2">
				</div>
				<div class="input-prepend">
					<span class="add-on">
						<i class="icon-lock"></i>
					</span>
					<input type="password"
					placeholder="密码" name="password" id="idpassword2">
				</div>
				<div class="input-prepend">
					<span class="add-on">
						<i class="icon-lock"></i>
					</span>
					<input type="password"
					placeholder="确定密码" name="password2" id="idpassword3">
				</div>
				 	<input type="hidden" name="utype" id="utype" value="2" />
					<button id="register-btn" class="button1">注 册</button>
			</fieldset>
		</form>
	</div>
</div>

