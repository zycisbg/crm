package com.zyc.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.CustomerActivity;
import com.zyc.crm.bean.Order;
import com.zyc.crm.mapper.OrderMapper;
import com.zyc.crm.model.Page;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;

	// 获取分页信息
	public Page<Order> getPage(String pageNoStr, Integer customerId) {

		long totalElements = orderMapper.getTotalElements(customerId);

		Page<Order> page = new Page<>(pageNoStr, (int) totalElements);

		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;

		int endIndex = page.getPageSize() + fromIndex;

		List<Order> content = orderMapper.getContent(customerId,
				fromIndex, endIndex);

		page.setContent(content);

		return page;
	}

	//通过id获取订单的所有信息
	public Order getOrder(Integer id) {
		return orderMapper.getOrder(id);
	}

}
