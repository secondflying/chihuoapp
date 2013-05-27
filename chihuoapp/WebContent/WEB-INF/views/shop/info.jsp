<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="餐厅信息" scope="request" />
<jsp:include page="../includes/header.jsp" />
<section id="basicInfo" class="well">
	<form:form action="/shop/info/basic" method="post" modelAttribute="basicInfo" cssStyle="margin:0;">
		<fieldset>
			<legend>基本信息</legend>
			餐厅编码：
			<form:input path="id" readonly="true" cssClass="input-xxlarge" />
			<form:hidden path="id" />
			<br />

			餐厅名称：
			<form:input path="name" name='name' id="name" cssClass="input-xxlarge" />
			<form:errors path="name" cssClass="error" />
			<br />

			餐厅电话：
			<form:input path="telephone" name='telephone' id="telephone" cssClass="input-xxlarge" />
			<form:errors path="telephone" cssClass="error" />
			<br />

			人均消费：
			<form:input path="average" name='average' id="average" cssClass="input-xxlarge" />
			<form:errors path="average" cssClass="error" />
			<br />

			餐厅描述：
			<form:textarea rows="5" path="description" name='description' id="description" cssClass="input-xxlarge" />
			<br />
			
			<c:if test="${not empty basic}">
				<div class="alert alert-success" id="basicSuccess">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong><c:out value="${basic}" /></strong>
				</div>
				
				<script>
					$("#basicSuccess").delay(2000).slideUp("slow");
				</script>

			</c:if>
			
			<input id="submit" type="submit" class="btn btn-success" value="保存" />
		</fieldset>
	</form:form>
</section>

<section id="imageInfo" class="well">
	<fieldset>
		<legend>餐厅图片</legend>
		<c:choose>
			<c:when test="${empty imageUrl}">
				<img src="../assets/img/noupload.png" id="restaurantImage" />
			</c:when>
			<c:otherwise>
				<%-- <img src="http://taochike-menuimages.stor.sinaapp.com/big/${restaurant.image}" id="restaurantImage" /> --%>
				<%-- <img src="http://localhost:8080/MenuImages/big/${restaurant.image}" id="restaurantImage" /> --%>
				<img src="${imageUrl}" id="restaurantImage" />
			</c:otherwise>
		</c:choose>
		<br />

		<form id="form1" action="/shop/info/image" method="post" enctype="multipart/form-data"  style="margin:0;">
			<div id="swfu-placeholder"></div>
			<div class="fieldset flash" id="fsUploadProgress"></div>
		</form>
	</fieldset>
</section>

<section id="addressInfo" class="well">
	<fieldset>
		<legend>位置信息</legend>
		<div id="mapContainer" style="height: 300px; border: solid 1px #cccccc"></div>
		<br />
		<form:form action="/shop/info/address" method="post" modelAttribute="basicInfo" cssStyle="margin:0;">
			<div style=" font-size:12px; color:#666;">在地址框中输入餐厅地址后，点击地图，地图定位到该地址，之后可拖动锚点微调地址</div>
			地址：
			<form:input path="address" cssClass="input-xlarge" />
			<form:errors path="address" cssClass="error" />
			<form:hidden path="id" name="id" />
			<form:hidden path="x" id="x" name="x" />
			<form:hidden path="y" id="y" name="y" />
			<br />

			<c:if test="${address != null}">
				<div class="alert alert-success"  id="addressSuccess">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong><c:out value="${address}" /></strong>
				</div>
				<script>
					$("#addressSuccess").delay(2000).slideUp("slow");
				</script>
			</c:if>
			<input id="submit" type="submit" class="btn btn-success" value="保存" />

		</form:form>
	</fieldset>
</section>

<script type="text/javascript" src="../assets/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="../assets/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript" src="../assets/SWFUpload/fileprogress.js"></script>
<script type="text/javascript" src="../assets/SWFUpload/handlers.js"></script>


<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>

<script>
	var gc = new BMap.Geocoder();
	var map = new BMap.Map("mapContainer");
	var point = new BMap.Point(116.404, 39.915); // 创建点坐标
	map.centerAndZoom(point, 12);
	map.enableScrollWheelZoom();

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
		
		$("#x").val(x);
		$("#y").val(y);
		
		//拖拽地图时触发事件
		marker.addEventListener("dragend", function(e) {
			MygetLocation(e.point);

			$("#x").val(e.point.lng);
			$("#y").val(e.point.lat);
			
		});

		map.centerAndZoom(point, 15);
	}

	$(document).ready(
					function() {
						$("#address").blur(function() {
							map.clearOverlays();
							MyLoad($("#address").val());
						});

						if ($("#x").val() && $("#y").val()) {
							drawMarker(parseFloat($("#x").val()), parseFloat($(
									"#y").val()));
						}

						var swfuOption = {
							button_placeholder_id : "swfu-placeholder",
							upload_url : "<c:url value="/shop/info/image;jsessionid=${pageContext.session.id}"/>", // Relative to the SWF file (or you can use absolute paths)

							flash_url : "../assets/SWFUpload/Flash/swfupload.swf",
/*  */							file_types : "*.jpg;*.gif;*.png",
							file_types : "*.*",
							file_size_limit : "20480000",
							file_post_name : "file",

							custom_settings : {
								progressTarget : "fsUploadProgress",
								imageTarget : "restaurantImage"
							},

							debug : false,
							prevent_swf_caching : false,
							preserve_relative_urls : false,

							button_image_url : "../assets/SWFUpload/buttons/TestImageNoText_65x29.png",
							button_width : 65,
							button_height : 29,
							button_text : "修改",

							// The event handler functions are defined in handlers.js
							file_queued_handler : fileQueued,
							file_queue_error_handler : fileQueueError,
							file_dialog_complete_handler : fileDialogComplete,
							upload_start_handler : uploadStart,
							upload_progress_handler : uploadProgress,
							upload_error_handler : uploadError,
							upload_success_handler : uploadSuccess,
							upload_complete_handler : uploadComplete,
							queue_complete_handler : queueComplete

						};
						var swfu = new SWFUpload(swfuOption);//初始化并将swfupload按钮替换swfupload占位符
					});
</script>
<jsp:include page="../includes/footer.jsp" />