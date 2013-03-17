<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="assets/js/jquery-latest.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/jquery/js/jquery.form.js"></script>
<script src="assets/js/bootbox.js"></script>
<script src="assets/js/config.js"></script>
<script>
	var options = {
		url : "",
		resetForm : true,
		success : function(responseText, statusText, xhr, $form) {
			loginCallback(responseText);
		},
		error : function(xhr, textStatus, errorThrown) {
			var div = $("#login-modal");
			div.animate({
				marginLeft : '+=25px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=25px'
			}, 50);
		}
	};

	$(document).ready(function() {
		$("#login-btn").click(function() {
			loginClick();
			return false;
		});
		$("#register-btn").click(function() {
			registerClick();
			return false;
		});
		$("#logout-a").click(function() {
			logoutClick();
			return false;
		});

		//判断是否已登录
		//获取用户信息，查看是否登录
		$.get(RESTURL + "userinfo").success(function(data) {
			loginCallback(data);
		}).error(function(xhr) {
			logoutCallback();
			/* if(xhr.status == 401){
				alert("请登录");
			}else if(xhr.status == 400){
				alert("用户不存在");
			} */
		});
	});

	function loginCallback(responseText) {
		$("#login-div").hide();
		$("#user-div").show();
		document.getElementById("user-btn").innerText = responseText.name;
		$('#login-modal').modal('hide');
		$('#signup-modal').modal('hide');
	}

	function logoutCallback() {
		$("#login-div").show();
		$("#user-div").hide();
	}

	function loginClick() {
		options.url = RESTURL + "ologin";
		options.error = function(xhr, textStatus, errorThrown) {
			var div = $("#login-modal");
			div.animate({
				marginLeft : '+=25px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=25px'
			}, 50);
			console.log(111);
		};
		$('#login-form').unbind('submit');
		$('#login-form').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});
		$('#login-form').submit();
		return false;
	}
	function registerClick() {
		options.url = RESTURL + "oregister";
		options.error = function(xhr, textStatus, errorThrown) {
			var div = $("#signup-modal");
			div.animate({
				marginLeft : '+=25px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=50px'
			}, 50);
			div.animate({
				marginLeft : '+=50px'
			}, 50);
			div.animate({
				marginLeft : '-=25px'
			}, 50);
			console.log(111);
		};
		$('#register-form').unbind('submit');
		$('#register-form').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});
		$('#register-form').submit();
		return false;
	}
	function logoutClick() {
		$.ajax({
			type : "POST",
			url : RESTURL + "logout",
			cache : false,
			success : function(data, textStatus, jqXHR) {
				logoutCallback();
				window.location.href = "index.jsp";
			},
			error : function(xhr, textStatus, errorThrown) {
				bootbox.alert(xhr.responseText);
			}
		});
	}
</script>
