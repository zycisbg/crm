package com.zyc.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Order;

public interface OrderMapper {

	long getTotalElements(@Param("customerId") Integer customerId);

	List<Order> getContent(@Param("customerId") Integer customerId,
			@Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);

	Order getOrder(@Param("id")Integer id);

}
