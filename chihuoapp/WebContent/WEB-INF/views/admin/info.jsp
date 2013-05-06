<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="assets/css/css.css" rel="stylesheet" media="screen">

</head>
<body>
	<tiles:insertAttribute name="top" />

	<div class="container">
		<div class="row">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li class="active"><a href="infoManager">餐厅信息</a></li>
					<li><a href="recipeManager">菜品维护</a></li>
					<li><a href="desksManager">餐桌维护</a></li>
					<li><a href="orderManager">订单管理</a></li>
					<li><a href="waiterManager">服务员管理</a></li>
				</ul>
			</div>
			<div class="span10">
				<form id="categoryForm" class="form-inline"
					enctype="multipart/form-data" method="post">

					<div class=" well">
						<h4>基本信息</h4>
						餐厅名称：<input type="text" name='name' id="name" /> <br /> 餐厅电话：<input
							type="text" name='telephone' id="telephone" />
						<!-- <br />
					餐厅介绍：<input type="text" id="description" /> -->
					</div>

					<div class=" well">
						<h4>餐厅图片</h4>
						<img src="" id="restaurantImage" /> <input type="file"
							name="image" id="image" />
					</div>

					<div class=" well">
						<h4>位置信息</h4>
						<div id="mapContainer"
							style="height: 300px; border: solid 1px #cccccc"></div>
						<br /> 地址：<input type="text" name="address" id="address" /> <input
							type="hidden" id="x" name="x" /> <input type="hidden" id="y"
							name="y" />
					</div>
				</form>
				<div class=" well">
					<a id="saveall" href="#" class="btn btn-success btn-large">保存</a>
				</div>
			</div>
		</div>
	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>

	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>

	<script>
		var gc = new BMap.Geocoder();
		var map = new BMap.Map("mapContainer");
		var point = new BMap.Point(116.404, 39.915);    // 创建点坐标
		map.centerAndZoom(point,12);  
		map.enableScrollWheelZoom();

		getRestaurants();

		//创建标注方法
		function MyLoad(StoreAddress) {
			gc.getPoint(StoreAddress, function(point) {
				if (point) {
					console.log(point);
					drawMarker(point.lng, point.lat);
				}
			}, "北京");
		}

		function MygetLocation(point) {
			gc.getLocation(point, function(rs) {
				$("#address").val(rs.address);
			});
		}

		function drawMarker(x, y) {
			var point = new BMap.Point(x, y);
			var marker = new BMap.Marker(point);
			marker.enableDragging();
			map.addOverlay(marker);
			console.log(2);


			//拖拽地图时触发事件
			marker.addEventListener("dragend", function(e) {
				//if (getAddress) {
				MygetLocation(e.point);
				//}
				console.log(e.point);

				$("#x").val(e.point.lng);
				$("#y").val(e.point.lat);
			});

			map.centerAndZoom(point, 15);
		}

		$(document).ready(function() {

			$("#address").blur(function() {
				map.clearOverlays();
				MyLoad($("#address").val());
			});

			$("#saveall").click(function() {
				$('#categoryForm').submit();
				return false;
			});

		});

		/* 获取餐厅 */
		function getRestaurants() {
			$.getJSON(RESTURL + "owner/restaurants",
					function(data) {
						if (data.length > 0) {
							var value = data[0];
							$("#name").val(value.name);
							$("#telephone").val(value.telephone);
							$("#address").val(value.address);

							if (value.image) {
								$("#restaurantImage").attr("src", IMAGEURL +  value.image);
							} else {
								$("#restaurantImage").attr("src",
										"assets/img/noupload.png");
							}
							
							if(value.x && value.y){
								drawMarker(value.x, value.y);
							}

							var options = {
								url : RESTURL + "restaurants/" + value.id,
								resetForm : false,
								success : function(responseText, statusText,
										xhr, $form) {
									//getCategories();
									window.location.href = "infoManager.jsp";
								},
								error : function(xhr, textStatus, errorThrown) {
									bootbox.alert(xhr.responseText);
								}
							};
							$('#categoryForm').unbind('submit');
							$('#categoryForm').submit(function() {
								$(this).ajaxSubmit(options);
								return false;
							});

							return false;
						} else {
							var options = {
								url : RESTURL + "restaurants",
								resetForm : false,
								success : function(responseText, statusText,
										xhr, $form) {
									//window.location.href = "infoManager.jsp";
								},
								error : function(xhr, textStatus, errorThrown) {
									bootbox.alert(xhr.responseText);
								}
							};
							$('#categoryForm').unbind('submit');
							$('#categoryForm').submit(function() {
								$(this).ajaxSubmit(options);
								return false;
							});

							return false;
						}
					});
		}
	</script>


</body>
</html>

