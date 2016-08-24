package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Product;
import com.zyc.crm.bean.SalesChance;

public interface ProductMapper {

	long getTotalElementsByMap(Map<String, Object> mybatisParams);

	List<SalesChance> getContentByMap(Map<String, Object> mybatisParams);

	void save(Product product);

	Product getProductById(@Param("id") Integer id);

	void update(Product product);

	void delete(@Param("id") Integer id);

}
