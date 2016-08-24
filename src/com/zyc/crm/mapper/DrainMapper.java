package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.CustomerDrain;
import com.zyc.crm.bean.SalesChance;

public interface DrainMapper {

	long getTotalElementsByMap(Map<String, Object> mybatisParams);

	List<SalesChance> getContentByMap(Map<String, Object> mybatisParams);

	CustomerDrain getCustomerDrainById(@Param("id") Integer id);

	void save(@Param("drainId") Integer drainId, @Param("delay") String delay);

	
	void updateStatus(CustomerDrain customerDrain);

}
