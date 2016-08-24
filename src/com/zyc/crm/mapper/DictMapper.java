package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.Dict;
import com.zyc.crm.bean.SalesChance;

public interface DictMapper extends BaseMapper<Dict>{

	/*long getTotalElements(Map<String, Object> mybatisParams);

	List<Dict> getContent(Map<String, Object> mybatisParams);*/

	void save(Dict dict);

	Dict getDictById(@Param("id")Integer id);

	void update(Dict dict);

	void delete(@Param("id")Integer id);

}
