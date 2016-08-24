package com.zyc.crm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.SalesPlan;

public interface SalesPlanMapper {
	
	//获取带查询条件的当前页面的集合
	List<SalesChance> getContentByMap(Map<String, Object> mybatisParams);

	//获取带查询条件的总的个数
	long getTotalElementsByMap(Map<String, Object> mybatisParams);

	//添加销售计划
	void save(SalesPlan salesPlan);

	//通过外键来查询当前销售机会的销售计划
	Set<SalesPlan> getPlanByChanceId(@Param("id")Integer id);

	//添加一个没有日期的计划
	void saveNoDate(SalesPlan salesPlan);

	//删除一个计划
	void delete(@Param("id")Integer id);

	//更新计划(只更新todo字段)
	void update(@Param("id")Integer id,@Param("todo") String todo);

	//更新结果(只更新result字段)
	void updateResult(@Param("id")Integer id,@Param("result") String result);

}
