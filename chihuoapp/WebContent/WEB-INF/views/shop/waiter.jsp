<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="菜品维护" scope="request" />
<jsp:include page="../includes/header.jsp" />

<section class="well">
	<fieldset>
		<legend>
			服务员列表
			<a class="btn btn-success pull-right" href="#addOne-modal" data-toggle="modal">
				<i class="icon-plus icon-white"></i> 新增
			</a>
		</legend>
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
					<th align="center">编号</th>
					<th align="center">名称</th>
					<th align="center">编辑</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty waiters}">
					<tr>
						<td colspan="4">无服务员</td>
					</tr>

				</c:if>
				<c:forEach items="${waiters}" var="waiter">
					<tr class="${cssClass}">
						<td><c:out value="${waiter.id}" /></td>
						<td><c:out value="${waiter.name}" /></td>
						<td>
							<button class="btn btn-small btn-warning" type="button" onclick="editOne('${waiter.id}','${waiter.name}','${waiter.password}')">编辑</button>
							<button class="btn btn-small btn-danger" type="button" onclick="deleteOne('${waiter.id}')">删除</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</section>


<div class="modal hide fade" id="addOne-modal" data-backdrop="static" data-keyboard="false">
	<c:url var="editUrl" value="/shop/waiter/edit" />
	<form id="addOne-form" class="form-horizontal" action="${editUrl }" method="post" enctype="multipart/form-data">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">服务员编辑</h3>
		</div>
		<br>
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">名称</label>
				<div class="controls">
					<input type="text" class="span4" name="addName" id="addName" placeholder="输入该服务员的名称，如：waiter01" />
					<input type="hidden" class="span4" name="addID" id="addID" placeholder="" />
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="capacity">密码</label>
				<div class="controls">
					<input type="text" class="span4" name="addPassword" id="addPassword" placeholder="输入该服务员的登录密码，如：123456" />
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



<script>
	$(document).ready(
			function() {

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

				$("#addOne-form").validate(
						{
							rules : {
								addName : {
									required : true
								},
								addPassword : {
									required : true,
									number : true
								}
							},

							messages : {
								addName : {
									required : "必填"
								},
								addPassword : {
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
							submitHandler : function(form) {
								form.submit();
							}
						});

			});

	function editOne(id, name, password) {
		$("#addID").val(id);
		$("#addName").val(name);
		$("#addPassword").val(password);
		$('#addOne-modal').modal('show');
	}

	function deleteOne(id) {
		bootbox.confirm("确定要删除吗？", "取消", "确定", function(isOk) {
			if (!isOk) {
				return;
			}

			$.post('<c:url value="/shop/waiter/delete" />', {
				id : id
			}).done(function(data) {
				window.location.href = window.location.href;
			}).fail(function() {
			});
		});
	}
</script>


<jsp:include page="../includes/footer.jsp" />