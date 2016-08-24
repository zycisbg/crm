package com.zyc.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zyc.crm.bean.Order;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.OrderService;

@Controller
public class OrderHandler {

	@Autowired
	private OrderService orderService;
	
	//跳转到查看详细信息的页面，。
	//通过订单查
	@RequestMapping("/order/details/{id}")
	public String showDetails(@PathVariable("id") Integer id,Map<String,Object> map){
		
		Order order = orderService.getOrder(id);
		
		map.put("order", order);
		
		return "order/details";
	}

	@RequestMapping("/order/list/{customerId}")
	public String list(@PathVariable("customerId") Integer customerId,
			@RequestParam(value = "pageNo", required = false) String pageNo,
			Map<String,Object> map) {

		Page<Order> page = orderService.getPage(pageNo,customerId);
		
		map.put("page",page);
		return "order/list";
	}
}
