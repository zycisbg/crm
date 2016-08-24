<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>暂缓流失</title>
<script type="text/javascript">
	$(function() {
		$("#submitforajax").click(function() {
			var delay = $("#delay").val();
			var drainId = $("#drainId").val();

			var url = "${ctp}/drain/delay-ajax";
			
			var args = {
				"drainId" : drainId,
				"delay" : delay
			};
			$.post(url, args, function(data) {

				var reg = /^\d+\$/g;
				var flag = reg.test(data);
				
				if (flag) {
					
					var $tr = $("<tr><th>暂缓措施-"+data+"</th><td colspan='3'>"+delay+"</td></tr>");
				
					
					/* $tr.append("<th>暂缓措施-"+data+"</th>")
					   .append("<td colspan='3'>"+delay+"</td>"); */
					
					$("#texttr").before($tr);
					   
					$("#texttr").val("");
					alert("保存成功");
				} else {
					alert("请输入暂缓措施");
				}
				
			});
			return false;
		});
	})
</script>
</head>

<span class="page_title">暂缓流失</span>
<div class="button_bar">
	<button class="common_button" onclick="javascript:history.go(-1);">返回</button>
	<button class="common_button"
		onclick="window.location.href='${ctp }/drain/confirm?id=${drain.id }'">确认流失</button>
	<button class="common_button" id="submitforajax">保存</button>
</div>

<body class="main">
	<form action="${ctp }/drain/delay" method="post">
		<input type="hidden" id="drainId" name="id" value="${drain.id}" />
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>编号</th>
				<td>${drain.id}</td>
				<th>客户</th>
				<td>${drain.customer.name}</td>
			</tr>
			<tr>
				<th>客户经理</th>
				<td>${drain.customer.manager.name}</td>
				<th>最后一次下单时间</th>
				<td><fmt:formatDate value="${drain.lastOrderDate }"
						pattern="yyyy-MM-dd" /></td>
			</tr>
			<c:forTokens items="${drain.delay}" delims="`" var="delay"
				varStatus="status">
				<c:if test="${delay != '' }">
					<tr>
						<th>暂缓措施-${status.count }</th>
						<td colspan="3">${delay}</td>
					</tr>
				</c:if>
			</c:forTokens>
			<tr id="texttr">
				<th>追加暂缓措施</th>
				<td colspan="3"><textarea id="delay" name="delay" cols="50"
						rows="6"></textarea>&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>