package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<T> getContent(Map<String, Object> mybatisParams);
}
