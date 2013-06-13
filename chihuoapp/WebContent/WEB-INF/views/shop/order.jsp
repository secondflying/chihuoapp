<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="订单管理" scope="request" />
<jsp:include page="../includes/header.jsp" />


<section class="well">
	<fieldset>
		<legend> 订单管理 </legend>
		<c:if test="${success != null}">
			<div class="alert alert-success" id="successMessage">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong><c:out value="${success}" /></strong>
			</div>
			<script>
				$("#successMessage").delay(2000).slideUp("slow");
			</script>
		</c:if>
		<table id="objList" class="table  table-bordered table-hover" style="margin: 0;">
			<thead>
				<tr>
					<th align="center">订单号</th>
					<th align="center">餐桌</th>
					<th align="center">就餐人数</th>
					<th align="center">开台时间</th>
					<th align="center">金额</th>
					<th align="center">状态</th>
					<th align="center">管理</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty orders}">
					<tr>
						<td colspan="4">无订单</td>
					</tr>

				</c:if>
				<c:forEach items="${orders}" var="ord">
					<tr class="${cssClass}">
						<fmt:formatDate value="${ord.starttime}" type="both" pattern="yyyy-MM-dd HH:mm" var="when" />

						<td><c:out value="${ord.code}" /></td>
						<td><c:out value="${ord.desk.name}" /></td>
						<td><c:out value="${ord.number}" /></td>
						<td><c:out value="${when}" /></td>
						<td><c:out value="${ord.priceAll}" /></td>
						<td>
							<c:choose>
								<c:when test="${ord.status == 1}">
									新开台
								</c:when>
								<c:when test="${ord.status == 3}">
									请求结账
								</c:when>
								<c:when test="${ord.status == 4}">
									已结账
								</c:when>
								<c:when test="${ord.status == 5}">
									撤单
								</c:when>
								<c:otherwise>
							   		未知状态
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<button class="btn btn-small btn-warning" type="button" onclick="editOne('${ord.id}')">详情</button> <c:choose>
								<c:when test="${ord.status == 1}">
									<button class="btn btn-small btn-warning" type="button" onclick="editOne('${ord.id}')">结账</button>
									<button class="btn btn-small btn-danger" type="button" onclick="editOne('${ord.id}')">撤单</button>
								</c:when>
								<c:when test="${ord.status == 3}">
									<button class="btn btn-small btn-warning" type="button" onclick="editOne('${ord.id}')">结账</button>
									<button class="btn btn-small btn-danger" type="button" onclick="editOne('${ord.id}')">撤单</button>
								</c:when>
								<c:when test="${ord.status == 4}">
								</c:when>
								<c:when test="${ord.status == 5}">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</section>

<div class="modal hide fade" id="addTypes-modal" data-backdrop="static" data-keyboard="false">
	<form id="addCate-form" class="form-horizontal" enctype="multipart/form-data" method="post">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">新增类型</h3>
		</div>
		<br>
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="span4" name="catename" id="catename" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<textarea rows="5" class="span4" name="catedescription" id="catedescription"></textarea>
					<span class="help-inline"></span>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label span2" for="login-btn"></label>
				<input type="submit" id="recipe-btn" value="确  定" class="btn btn-success span3">
			</div>
		</fieldset>
	</form>
</div>

<div class="modal hide fade" id="addOne-modal" data-backdrop="static" data-keyboard="false">
	<c:url var="recipeAddUrl" value="/shop/recipe/addone" />
	<form id="addOne-form" class="form-horizontal" action="${recipeAddUrl }" method="post" enctype="multipart/form-data">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">新增菜品</h3>
		</div>
		<br>
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="span4" name="addName" id="addName" placeholder="输入该菜品的名称，如：招牌红烧肉" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="capacity">价格</label>
				<div class="controls">
					<input type="text" class="span4" name="addPrice" id="addPrice" placeholder="输入该菜品的价格，如：48" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="capacity">图片</label>
				<div class="controls">
					<input type="file" class="span4" name="addImage" id="addImage" placeholder="选择菜品图片" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">描述</label>
				<div class="controls">
					<textarea rows="5" class="span4" name="addDescription" id="addDescription"></textarea>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="cate">类型</label>
				<div class="controls">
					<select class="span4" name="addCateList" id="addCateList">
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


<div class="modal hide fade" id="editOne-modal" data-backdrop="static" data-keyboard="false">
	<c:url var="recipeEditUrl" value="/shop/recipe/editone" />
	<form id="editOne-form" class="form-horizontal" action="${recipeEditUrl }" enctype="application/x-www-form-urlencoded" method="post">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">菜品编辑</h3>
		</div>
		<br>
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="span4" name="editName" id="editName" placeholder="输入该菜品的名称，如：招牌红烧肉" />
					<span class="help-inline"></span>
					<input type="hidden" class="span4" name="editID" id="editID" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="capacity">价格</label>
				<div class="controls">
					<input type="text" class="span4" name="editPrice" id="editPrice" placeholder="输入该菜品的价格，如：48" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">描述</label>
				<div class="controls">
					<textarea rows="5" class="span4" name="editDescription" id="editDescription"></textarea>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="cate">类型</label>
				<div class="controls">
					<select class="span4" name="editCateList" id="editCateList">
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
				var cid = t ? "cate_" + t : "cate_all";

				$("#cateList li").attr({
					class : ""
				});
				$("#" + cid).attr({
					class : "active"
				});

				if (t) {
					$("#catelist").val(t);
				}

				//表格排序
				$('#objList').dataTable({
					"bPaginate" : false,
					"bLengthChange" : false,
					"bFilter" : false,
					"bSort" : true,
					"bInfo" : false,
					"bAutoWidth" : false,
					"aoColumnDefs" : [ {
						'bSortable' : false,
						'aTargets' : [ 0 ]
					}, {
						'bSortable' : false,
						'aTargets' : [ -1 ]
					} ]
				});

				$("#addCate-form").validate(
						{
							rules : {
								catename : {
									required : true
								}
							},

							messages : {
								catename : {
									required : "必填"
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
								cateEditConfirm();
								return false;
							}
						});

				$("#addOne-form").validate(
						{
							rules : {
								addName : {
									required : true
								},
								addPrice : {
									required : true,
									number : true
								},
								addImage : {
									required : true
								}
							},

							messages : {
								addName : {
									required : "必填"
								},
								addPrice : {
									required : "必填",
									number : "填入数字"
								},
								addImage : {
									required : "选择图片"
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
							submitHandler : function(form) {
								form.submit();
							}
						});

				$("#editOne-form").validate(
						{
							rules : {
								editName : {
									required : true
								},
								editPrice : {
									required : true,
									number : true
								}
							},

							messages : {
								editName : {
									required : "必填"
								},
								editPrice : {
									required : "必填",
									number : "填入数字"
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
							submitHandler : function(form) {
								form.submit();
							}
						});

			});

	function cateEditConfirm() {
		var name = $("#catename").val();
		var description = $("#catedescription").val();

		$.post('<c:url value="/shop/recipe/cate" />', {
			name : name,
			description : description
		}).done(function(data) {
			window.location.href = window.location.href;
		}).fail(function() {
		});

		return false;
	}

	function editOne(id, name, price, description, cate) {
		$("#editID").val(id);
		$("#editName").val(name);
		$("#editPrice").val(price);
		$("#editDescription").val(description);
		$("#editCateList").val(cate);
		$('#editOne-modal').modal('show');
	}

	function deleteOne(id) {
		bootbox.confirm("确定要删除吗？", "取消", "确定", function(isOk) {
			if (!isOk) {
				return;
			}

			$.post('<c:url value="/shop/recipe/delete" />', {
				id : id
			}).done(function(data) {
				window.location.href = window.location.href;
			}).fail(function() {
			});
		});
	}
</script>


<jsp:include page="../includes/footer.jsp" />