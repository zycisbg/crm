package com.zyc.crm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.crm.bean.SalesChance;
import com.zyc.crm.bean.SalesPlan;
import com.zyc.crm.mapper.SalesPlanMapper;
import com.zyc.crm.model.Page;
import com.zyc.crm.model.PropertyFilter;
import com.zyc.crm.util.CRMUtils;

@Service
public class SalesPlanService {

	@Autowired
	private SalesPlanMapper salesPlanMapper;

	/**
	 * 查询带条件的分页。 分页的参数在paramsMap中
	 * 整体思路：先把handler传来的params通过属性过滤器转为PropertyFilter的集合 在把这个集合转为
	 * mabatis可以用的参数map
	 * 
	 * @param pageNoStr
	 * @param params
	 * @return
	 */
	@Transactional
	public Page<SalesChance> getPage(String pageNoStr,
			Map<String, Object> params) {
		// 1查询总的记录数，需要先转换handler传来的参数

		// 把handler传来的参数转为propertyFilter的集合
		List<PropertyFilter> filters = CRMUtils
				.parseHandlerParamsToPropertyFilters(params);
		// 把propertyFilter集合转为mybatis可以用的参数
		Map<String, Object> mybatisParams = CRMUtils
				.parsePropertyFiltersToMyBatisParmas(filters);

		// 通过mabtaisParams 查询 带条件的总的记录数
		long totalElements = salesPlanMapper
				.getTotalElementsByMap(mybatisParams);

		// 获取总个数后创建page对象(为了约束pageNo)
		Page<SalesChance> page = new Page<>(pageNoStr, (int) totalElements);

		// 2.获取当前页面的List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		// 把分页所需要的参数传入mabatisparams中查询当前页面的list
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<SalesChance> content = salesPlanMapper
				.getContentByMap(mybatisParams);
		page.setContent(content);

		return page;
	}

	//添加销售计划
	@Transactional
	public void save(SalesPlan salesPlan) {

		salesPlanMapper.save(salesPlan);
	}

	//通过外键来查询当前销售机会的销售计划
	@Transactional
	public Set<SalesPlan> getPlanByChanceId(Integer id) {
		// TODO Auto-generated method stub
		return salesPlanMapper.getPlanByChanceId(id);
	}

	//新建一个没有日期的计划
	@Transactional
	public void saveNoDate(SalesPlan salesPlan) {

		salesPlanMapper.saveNoDate(salesPlan);
	}

	//删除计划
	@Transactional
	public void delete(Integer id) {
		salesPlanMapper.delete(id);
	}

	//更新计划(只更新todo字段)
	@Transactional
	public void update(Integer id, String todo) {
		salesPlanMapper.update(id,todo);
	}

	//更新结果(只更新result字段)
	@Transactional
	public void updateResult(Integer id, String result) {

		salesPlanMapper.updateResult(id,result);
	}
}
