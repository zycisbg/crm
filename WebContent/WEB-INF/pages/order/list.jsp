<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="zyc" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
  <head>
    <title>查看历史订单</title>
  </head>  
  <body>
  	
  
  	<div class="page_title">查看历史订单</div>
  	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">返回</button>  
	</div>
		
	<br />
	<c:if test="${page != null && page.totalElements > 0 }">
		<table class="data_list_table" border="0" cellPadding="3" cellSpacing="0">
		<tr>
			<th>订单编号</th>
			<th>日期</th>
			<th>送货地址</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
			<c:forEach var="order" items="${page.content }">
				<tr>
					<td class="list_data_number">${order.no}</td>
					<td class="list_data_text"><fmt:formatDate value="${order.date }" pattern="yyyy-MM-dd"/></td>
					<td class="list_data_ltext">${order.address}</td>
					<td class="list_data_text">${order.status}</td>
					<td class="list_data_op">
						<img onclick="window.location.href='${ctp }/order/details/${order.id }'" 
						title="查看明细" src="${ctp }/static/images/bt_detail.gif" class="op_button" />
					</td>
				</tr>			
			</c:forEach>
		</table>
		<%-- <tags:pagination page="${page}" paginationSize="5" condition="customerid=${param.customerid}"/> --%>
		<zyc:page page="${page }" ></zyc:page>
	</c:if>
	<c:if test="${page == null || page.totalElements == 0 }">
		没有任何数据
	</c:if>
  </body>
</html>