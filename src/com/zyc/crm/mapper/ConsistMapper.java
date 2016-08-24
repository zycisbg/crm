package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;


public interface ConsistMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Object[]> getContent(Map<String, Object> mybatisParams);

}
