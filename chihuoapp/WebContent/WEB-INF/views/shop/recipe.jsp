<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="菜品维护" scope="request" />
<jsp:include page="../includes/header.jsp" />

<section class="well">
	<fieldset>
		<legend>
			菜品类型
			<a class="btn btn-success pull-right" href="#addTypes-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i> 新增
			</a>
		</legend>
		<ul id="cateList" class="nav nav-pills" style="margin: 0;">
			<c:choose>
				<c:when test="${empty types}">
				尚未添加菜品分类
			</c:when>
				<c:otherwise>
					<c:url var="infoUrl" value="/shop/recipe" />
					<li id="cate_all" class="active"><a href="${infoUrl }">所有</a></li>
				</c:otherwise>
			</c:choose>

			<c:forEach items="${types}" var="t">
				<c:set var="cate" value="${t.id }"></c:set>
				<c:url var="infoUrl" value="/shop/recipe?cate=${cate }" />
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
			菜品列表
			<a class="btn btn-success pull-right" href="#addOne-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i> 新增
			</a>
			<a class="btn btn-success pull-right" href="#addBatch-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i>批量新增
			</a>
		</legend>
		<c:if test="${success != null}">
				<div class="alert alert-success"  id="successMessage">
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
					<th align="center">图片</th>
					<th align="center">名称</th>
					<th align="center">价格</th>
					<th align="center">编辑</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty recipes}">
					<tr>
						<td colspan="4">无菜品</td>
					</tr>

				</c:if>
				<c:forEach items="${recipes}" var="recipe">
					<tr class="${cssClass}">
						<td><img src="${imageUrl}/${recipe.image}" id="restaurantImage" /></td>
						<td><c:out value="${recipe.name}" /></td>
						<td><c:out value="${recipe.price}" /></td>
						<td>
							<button class="btn btn-small btn-warning" type="button" onclick="editOne('${recipe.id}','${recipe.name}','${recipe.price}','${recipe.description}','${recipe.category.id}')">编辑</button>
							<button class="btn btn-small btn-danger" type="button" onclick="deleteOne('${recipe.id}')">删除</button>
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
				<label class="control-label" for="name">描述</label>
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