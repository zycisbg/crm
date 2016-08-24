<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="zyc" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>管理</title>
</head>
<body>
	<div class="page_title">
		基础数据管理
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/dict/create'">
			新建
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp}/dict/list" method="POST">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					类别
				</th>
				<td>
					<input type="text" name="search_LIKES_type" />
				</td>
				<th>
					条目
				</th>
				<td>
					<input type="text" name="search_LIKES_item" />
				</td>
				<th>
					值
				</th>
				<td>
					<input type="text" name="search_LIKES_value" />
				</td>
			</tr>
		</table>
	</form>
	<!-- 列表数据 -->
	<br />
	
	<c:if test="${page != null && page.totalElements > 0 }">
		<table class="data_list_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					编号
				</th>
				<th>
					类别
				</th>
				<th>
					条目
				</th>
				<th>
					值
				</th>
				<th>
					操作
				</th>
			</tr>
			<c:forEach var="dict" items="${page.content }">
				<tr>
					<td class="list_data_number">
						${dict.id}
					</td>
					<td class="list_data_text">
						${dict.type}
					</td>
					<td class="list_data_text">
						${dict.item}
					</td>
					<td class="list_data_text">
						${dict.value}
					</td>

					<td class="list_data_op">
						<c:if test="${dict.editable}">
							<img onclick="window.location.href='${ctp}/dict/create/${dict.id }?pageNo=${page.pageNo }'" 
								title="编辑" src="${ctp}/static/images/bt_edit.gif" class="op_button" />
							<img onclick="window.location.href='${ctp}/dict/delete/${dict.id }?pageNo=${page.pageNo }'" 
								title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
						</c:if>
					</td>
				</tr>
			</c:forEach>			
		</table>
		<zyc:page page="${page }" ></zyc:page>
	</c:if>
	<c:if test="${page == null || page.totalElements == 0 }">
		没有任何数据
	</c:if>
	
</body>
</html>