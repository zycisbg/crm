<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib prefix="zyc" tagdir="/WEB-INF/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客户基本信息管理</title>
<script type="text/javascript">
	$(function() {
		$("img[id^='delete']").click(function() {
			var id = $(this).attr("id");
			id = id.split("-")[1];

			var url = "${ctp}/customer/delete-ajax";
			var args = {
				"id" : id
			};
			$.post(url, args, function(data) {
				if (data == "1") {
					$("#delete-" + id).remove();
					alert("删除成功!");
				} else {
					alert("删除失败!");
				}
			});

			return false;
		});
	});
</script>
</head>
<body>

	<div class="page_title">客户基本信息管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">查询</button>
	</div>

	<form action="${ctp }/customer/showList" method="POST">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>客户名称</th>
				<td><input type="text" name="search_LIKES_name" /></td>
				<th>地区</th>
				<td><select name="search_EQS_region">
						<option value="">全部</option>
						<c:forEach items="${regions }" var="region">
							<option value="${region }">${region }</option>
						</c:forEach>
				</select></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>客户经理</th>
				<td><input type="text" name="search_LIKES_manager" /></td>

				<th>客户等级</th>
				<td><select name="search_EQS_level">
						<option value="">全部</option>
						<c:forEach items="${levels }" var="level">
							<option value="${level }">${level }</option>
						</c:forEach>
				</select></td>

				<th>状态</th>
				<td><select name="search_EQS_state">
						<option value="">全部</option>
						<option value="正常">正常</option>
						<option value="流失">流失</option>
						<option value="删除">删除</option>
						<option value="流失预警">删除</option>
				</select></td>
			</tr>
		</table>

		<!-- 列表数据 -->
		<br />

		<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>客户编号</th>
					<th>客户名称</th>
					<th>地区</th>
					<th>客户经理</th>
					<th>客户等级</th>
					<th>状态</th>
					<th>操作</th>
				</tr>

				<c:forEach var="customer" items="${page.content }">
					<tr>
						<td class="list_data_text">${customer.no }&nbsp;</td>
						<td class="list_data_ltext">${customer.name }&nbsp;</td>
						<td class="list_data_text">${customer.region }&nbsp;</td>
						<td class="list_data_text">${customer.manager.name }&nbsp;</td>
						<td class="list_data_text">${customer.level }&nbsp;</td>
						<td class="list_data_text">${customer.state}&nbsp;</td>
						<td class="list_data_op"><img
							onclick="window.location.href='${ctp}/customer/create/${customer.id}'"
							title="编辑" src="${ctp }/static/images/bt_edit.gif"
							class="op_button" alt="" /> <img
							onclick="window.location.href='${ctp}/contact/list/${customer.id }'"
							title="联系人" src="${ctp }/static/images/bt_linkman.gif"
							class="op_button" alt="联系人信息" /> <img
							onclick="window.location.href='${ctp}/activity/list/${customer.id }'"
							title="交往记录" src="${ctp }/static/images/bt_acti.gif"
							class="op_button" alt="交往记录" /> <img
							onclick="window.location.href='${ctp}/order/list/${customer.id }'"
							title="历史订单" src="${ctp }/static/images/bt_orders.gif"
							class="op_button" alt="历史订单" /> <c:if
								test="${customer.state != '删除' }">
								<img id="delete-${customer.id}" title="删除"
									src="${ctp }/static/images/bt_del.gif" class="op_button"
									alt="删除" />
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
			<zyc:page page="${page }"></zyc:page>
		</c:if>
		<c:if test="${page == null || page.totalElements == 0 }">
			没有任何数据
		</c:if>
	</form>

</body>
</html>
