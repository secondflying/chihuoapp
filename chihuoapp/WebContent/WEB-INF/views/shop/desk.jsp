<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="餐桌维护" scope="request" />
<jsp:include page="../includes/header.jsp" />

<section class="well">
	<fieldset>
		<legend>
			餐桌类型
			<a class="btn btn-success pull-right" href="#addTypes-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i> 新增
			</a>
		</legend>
		<ul id="cateList" class="nav nav-pills" style="margin: 0;">
			<c:choose>
				<c:when test="${empty types}">
				尚未添加餐桌类型
			</c:when>
				<c:otherwise>
					<c:url var="infoUrl" value="/shop/desk" />
					<li id="cate_all" class="active"><a href="${infoUrl }">所有</a></li>
				</c:otherwise>
			</c:choose>

			<c:forEach items="${types}" var="t">
				<c:set var="cate" value="${t.id }"></c:set>
				<c:url var="infoUrl" value="/shop/desk?cate=${cate }" />
				<li id="cate_${cate}" class=""><a href="${infoUrl }">
						<c:out value="${t.name}" />
					</a></li>
			</c:forEach>
		</ul>

	</fieldset>
</section>

<section class="well">
	<fieldset>
		<legend>
			餐桌列表
			<a class="btn btn-success pull-right" href="#addDesk-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i> 新增
			</a>
		</legend>
		<table id="deskList" class="table  table-bordered table-hover" style="margin: 0;">
			<thead>
				<tr>
					<th align="center">名称</th>
					<th align="center">容量</th>
					<!-- <th align="center">二维码</th> -->
					<th align="center">状态</th>
					<th align="center">编辑</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty desks}">
					<tr>
						<td colspan="4">无桌子</td>
					</tr>

				</c:if>
				<c:forEach items="${desks}" var="desk">
					<c:set var="cssClass" value="success" />
					<c:set var="statusString" value="空闲" />
					<c:if test="${!empty desk.orderStatus}">
						<c:set var="cssClass" value="error" />
						<c:set var="statusString" value="正在就餐" />
					</c:if>
					<tr class="${cssClass}">
						<td><c:out value="${desk.name}" /></td>
						<td><c:out value="${desk.capacity}" /></td>
						<td><c:out value="${statusString}" /></td>
						<td>
							<button class="btn btn-small btn-warning" type="button" onclick="editDesk('${desk.id}','${desk.name}','${desk.capacity}','${desk.tid}')">编辑</button>
							<button class="btn btn-small btn-danger" type="button" onclick="deleteDesk('${desk.id}')">删除</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</section>

<div class="modal hide fade" id="addTypes-modal">
	<form class="form-horizontal" enctype="multipart/form-data" method="post">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">新增类型</h3>
		</div>
		<br>
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="input-xlarge span4" name="catename" id="catename" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label span2" for="login-btn"></label>
				<input type="button" id="recipe-btn" value="确  定" class="btn btn-success span2" onclick="cateEditConfirm();">
			</div>
		</fieldset>
	</form>
</div>

<div class="modal hide fade" id="addDesk-modal">
	<form id="addDesk-form" class="form-horizontal" method="post">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">餐桌编辑</h3>
		</div>
		<br>
		<fieldset>
			<div id="nameDiv" class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="span4" name="dname" id="dname" placeholder="输入该餐桌的名称，如：一号桌" />
					<span class="help-inline"></span>
					<input type="hidden" class="span4" name="did" id="did" />
				</div>
			</div>
			<div id="capacityDiv" class="control-group">
				<label class="control-label" for="capacity">容量</label>
				<div class="controls">
					<input type="text" class="span4" name="capacity" id="capacity" placeholder="输入该桌能容纳的人数，如：4" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="cate">类型</label>
				<div class="controls">
					<select class="span4" name="cate" id="cate">

						<c:forEach items="${types}" var="t">
							<option value="${t.id }">
								<c:out value="${t.name}" />
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label span2" for="login-btn"></label>
				<input type="submit" id="recipe-btn" value="确  定" class="btn btn-success span3">
			</div>
		</fieldset>
	</form>
</div>

<script>
	$(document).ready(
			function() {
				var t = '${param.cate}';

				//
				$("#cateList li").attr({
					class : ""
				});
				var cid = "cate_all";
				if (t) {
					cid = "cate_" + t;
				}
				$("#" + cid).attr({
					class : "active"
				});

				//
				if (t) {
					$("#cate").val(t);
				}

				$('#deskList').dataTable({
					"bPaginate" : false,
					"bLengthChange" : false,
					"bFilter" : false,
					"bSort" : true,
					"bInfo" : false,
					"bAutoWidth" : false,
					"aoColumnDefs" : [ {
						'bSortable' : false,
						'aTargets' : [ 3 ]
					} ]
				});

				$("#addDesk-form").validate(
						{
							rules : {
								dname : {
									//remote: "<c:url value="/shop/desk/checknameexist" />",
									required : true
								},
								capacity : {
									required : true,
									number : true
								}
							},

							messages : {
								dname : {
									//remote: jQuery.format("已有名称为'{0}'的餐桌"),
									required : "必填"
								},
								capacity : {
									required : "必填",
									number : "填入整数"
								}
							},

							errorClass : 'invalid',
							validClass : 'valid',
							errorPlacement : function(error, element) {
								element.nextAll(".help-inline").html(error);
								element.parents(".control-group").removeClass(
										"success").addClass("error");

							},
							success : function(label) {
								label.parents(".control-group").removeClass(
										"error").addClass("success");
								label.addClass("valid").text("Ok!");
							},
							submitHandler : function() {
								deskEditConfirm();
								return false;
							}
						});

			});

	/* 新增餐桌类 */
	function cateEditConfirm() {
		var cate = $("#catename").val();

		$.post('<c:url value="/shop/desk/cate" />', {
			name : cate
		}).done(function(data) {
			window.location.href = window.location.href;
		}).fail(function() {
			//alert("error");
		});

		return false;
	}

	/* 删除餐桌类 */
	function deleteCategory() {
		bootbox.confirm("确定要删除当前选中的类别吗？", "取消", "确定", function(isOk) {
			if (!isOk) {
				return;
			}
			$.ajax({
				type : "DELETE",
				url : RESTURL + "restaurants/" + restaurant.id + "/desktypes/"
						+ selectCid,
				cache : false,
				success : function(data, textStatus, jqXHR) {
					selectCid = null;
					getCategories();
				},
				error : function(xhr, textStatus, errorThrown) {
					bootbox.alert(xhr.responseText);
				}
			});
		});

	}

	/* 新增餐桌 */
	function deskEditConfirm() {
		var id = $("#did").val();
		var name = $("#dname").val();
		var capacity = $("#capacity").val();
		var tid = $("#cate").val();

		$.post("<c:url value="/shop/desk/info" />", {
			id : id,
			name : name,
			capacity : capacity,
			tid : tid
		}).done(function(data) {
			window.location.href = window.location.href;
		}).fail(function() {
			//alert("error");
		});

		return false;
	}

	/* 编辑餐桌 */
	function editDesk(id, name, capacity, tid) {
		$("#dname").val(name ? name : "");
		$("#capacity").val(capacity ? capacity : "");
		$("#tid").val(tid);
		$("#did").val(id);
		$('#addDesk-modal').modal('show');
	}

	/* 删除餐桌 */
	function deleteDesk(id) {
		bootbox.confirm("确定要删除吗？", "取消", "确定", function(isOk) {
			if (!isOk) {
				return;
			}

			$.post('<c:url value="/shop/desk/delete" />', {
				id : id
			}).done(function(data) {
				window.location.href = window.location.href;
			}).fail(function() {
			});
		});
	}
</script>


<jsp:include page="../includes/footer.jsp" />