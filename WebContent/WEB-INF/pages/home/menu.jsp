<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单</title>
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/icon.css">
<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctp}/static/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function(){
		/*
		 * 1. 打开 easyui 的帮助文档, 找到 tree 这个文档页面
		 * 2. 搜索 "click". 先为节点添加 click 响应函数
		 * $('#tt').tree({
		 *		onClick: function(node){
		 *			alert(node.text);  // 在用户点击的时候提示
		 *		}
		 *	});
		 * 1). 可以添加 click 响应函数
		 * 2). 可以在 onclick 函数内部使用 node 节点的属性. 例如: id, text, state 等.
		 * 3. 为 node 节点添加 url: 在文档上搜索 "url". 可以知道添加 url 的方式为:
		 * 通过 attributes 属性来添加自定义的其他属性, 又是一个 JSON 格式
		 * "attributes":{    
	     *       "url":"/demo/book/abc",    
	     *       "price":100    
	     * }  
		 * 4. 如何在 onclick 响应函数中获取 url ? 可以通过 node.text 的方式来获取 text 属性. 则也就可以通过
		 * node.attributes.url 来获取 url 属性. 
		 * 5. 在 javascript 中 undefined 可以作为 false 来使用. 所以可以在 if 条件判断中添加
		 * node.attributes.url . 即若 node 有 attributes.url 属性则可以进入 if 判断条件. 否则不能进入 if 判断条件.
		 * 6. 在 frameset 的视角下, 使某一个 frame 的 src 发生修改
		 * window.parent.document.getElementById("content").src = node.attributes.url;
		 */
		$('#tt').tree({
			onClick: function(node){
				if(node.url){
					//alert(node.attributes.url);
					window.parent.document.getElementById("content").src = node.url;
				}
			}
		});
	})
</script>
</head>
<body>

	<ul id="tt" class="easyui-tree" data-options="url:'${ctp}/user/navigate',method:'get',animate:true"></ul>

</body>
</html>