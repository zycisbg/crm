<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="zyc" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>客户构成分析</title>
</head>
<body>
	${page.content }
	<div class="page_title">
		客户构成分析
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp }/report/consist">
		<div id="listView" style="display:block;">
			<table class="query_form_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>
						查询方式
					</th>
					<td>
						<select name="search_EQS_type">
							<option value="客户等级">
								按等级
							</option>
							<option value="信用度">
								按信用度
							</option>
							<option value="满意度">
								按满意度
							</option>
						</select>
					</td>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			<!-- 列表数据 -->
			<br />
			
			<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>
						序号
					</th>
					<th>
						${param.type}
					</th>
					<th>
						客户数量
					</th>
				</tr>
				
				<c:forEach var="objects" items="${page.content }" varStatus="status">
					<tr>
						<td class="list_data_number"></td>
						<td class="list_data_ltext"></td>
						<td class="list_data_number"></td>
					</tr>
				</c:forEach>
			</table>
			<zyc:page page="${page }"></zyc:page>
			</c:if>
			<c:if test="${page == null || page.totalElements == 0 }">
				没有任何数据
			</c:if>
		</div>
	</form>
</body>
</html>